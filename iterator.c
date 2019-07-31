// Iterator.c ver 3.1
//Last Update: Added Bash Commands to get PBA
#include<sys/ioctl.h>
#include <stdlib.h>
#include <sys/stat.h>
#include <unistd.h>
#include <fcntl.h>
#include <linux/fs.h>
#include <assert.h>
#include <dirent.h>
#include <limits.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
//Directory Looping Function
static int ptree(char *curpath, char * const path);
//LBA Extraction Function
void fibmap_fun(char name[20]);
int caller=0;//for choosing between printing the name only or with details
void extract_pba();
unsigned long int pba[830000];
FILE *output;

int main(int argc, char * const argv[])
{
  //Step0: Clear Trace File
	system("echo > /sys/kernel/debug/tracing/trace");
  //Step1 : Check trace file size
  FILE *f1;
  char c[25];
  int i=0;
  long val;
  system("cat /sys/kernel/debug/tracing/buffer_size_kb > buffer_size.txt");

  f1=fopen("buffer_size.txt","r");
  while((c[i] = fgetc(f1)) != EOF) i++;

  c[i+1]='\0';
  fclose(f1);

  char *p=c;
  while (*p)
  {
        if ( isdigit(*p) || ( (*p=='-'||*p=='+') && isdigit(*(p+1)) ))
        {
          val = strtol(p, &p, 10);
        //  printf("%ld\n", val);
        }
        else
        {
          p++;
        }
  }

  //Step2: Resize Trace File if Necessary
  if(val==1410) system("echo 780000 > /sys/kernel/debug/tracing/buffer_size_kb");

  //Step3: Create PBLK Instance

  int create_error =  system("sudo nvme lnvm create -d nvme0n1 -n mydevice -t pblk -b 0 -e 3");
  if(create_error != 0 ){
  	system("sudo umount /mnt/nvme");
	system("sudo nvme lnvm remove -n mydevice");
  
  	system("sudo nvme lnvm create -d nvme0n1 -n mydevice -t pblk -b 0 -e 3");
 	printf("mydevice is newly created.\n-------------------------------\n\n");
  }
  
  system("sudo mount /dev/mydevice /mnt/nvme");
  //Step4: invoke pblk_sysfs.c functions by check test_kdy
  system("cat /sys/block/mydevice/pblk/test_kdy");
 
  
  //Step5: EXTRACT PBA from trace file  -> make a function and call it inside fibmap
  extract_pba();
  
  system("echo > output.txt");	
  output = fopen("output.txt", "w");
  if(output == NULL){
	  printf("File pointer error!\n\n");
	  exit(1);
  }

  int k;
  int rval;

  for (rval = 0, k = 1; k < argc; k++)
  if (ptree(NULL, argv[k]) != 0)
  rval = 1;
  //calling one more time to print details
  fprintf(output, "END\n");
  caller=1;
  rval=0;
  for (rval = 0, k = 1; k < argc; k++)
  if (ptree(NULL, argv[k]) != 0)
  rval = 1;
 
  //Step6: Remove PBLK
  system("sudo umount /mnt/nvme");
  system("sudo nvme lnvm remove -n mydevice");

  return rval;


}

static int ptree(char *curpath, char * const path)
{

  char ep[PATH_MAX];
  char p[PATH_MAX];
  DIR *dirp;
  struct dirent entry;
  struct dirent *endp;
  struct stat st;


  if (curpath != NULL)
  snprintf(ep, sizeof(ep), "%s/%s", curpath, path);
  else
  snprintf(ep, sizeof(ep), "%s", path);
  if (stat(ep, &st) == -1)
  return -1;
  if ((dirp = opendir(ep)) == NULL)
  return -1;
  //main loop for iterating through the file
  for (;;) {
    endp = NULL;
    if (readdir_r(dirp, &entry, &endp) == -1) {
      closedir(dirp);
      return -1;
    }
    if (endp == NULL)
    break;
    assert(endp == &entry);
    if (strcmp(entry.d_name, ".") == 0 ||
    strcmp(entry.d_name, "..") == 0)
    continue;
    if (curpath != NULL)
    snprintf(ep, sizeof(ep), "%s/%s/%s", curpath,
    path, entry.d_name);
    else
    snprintf(ep, sizeof(ep), "%s/%s", path,
    entry.d_name);
    if (stat(ep, &st) == -1) {
      closedir(dirp);
      return -1;
    }
    if (S_ISREG(st.st_mode) || S_ISDIR(st.st_mode))//main condition
    {

      char new_name[50];//increase the size if the names are too long
      int counter=0;
      int counter2=0;
      while(ep[counter]!='\0')
      {
        new_name[counter2]=ep[counter];
        counter2++;
        counter++;
      }

      new_name[counter2]='\0';

      //loop backward until the code finds a backslash / -> use new_name for full name with path notation
      //1) find the size of new_name ==counter2

      char directory_file= S_ISDIR(st.st_mode) ? 'd' : 'f';
      if(directory_file=='f')//if file
      {

        int k=0;

        for(k=counter2;;k--)
        {
          if(new_name[k]=='/')
          {
            k++;
            break;
          }
        }
        while(k<=counter2)
        {
         fprintf(output,"%c",new_name[k]);
          k++;
        }
        fprintf(output, "\n");

        if(caller==1)
        {
          fibmap_fun(new_name);
        }
    }


  }

  if (S_ISDIR(st.st_mode) == 0)
  continue;
  if (curpath != NULL)
  snprintf(p, sizeof(p), "%s/%s", curpath, path);
  else
  snprintf(p, sizeof(p), "%s", path);

  snprintf(ep, sizeof(ep), "%s", entry.d_name);


  ptree(p, ep);
}
closedir(dirp);

return 0;
}


void fibmap_fun(char file_name[20])
{


  int fd, i, block, blocksize, blkcnt;
  struct stat st;

  assert(file_name != NULL);

  fd = open(file_name, O_RDONLY);
  if (fd <= 0) {
    perror("error opening file");
    goto end;
  }

  if (ioctl(fd, FIGETBSZ, &blocksize)) {
    perror("FIBMAP ioctl failed");
    goto end;
  }

  if (fstat(fd, &st)) {
    perror("fstat error");
    goto end;
  }

  blkcnt = (st.st_size + blocksize - 1) / blocksize;

  for (i = 0; i < blkcnt; i++) {
    block = i;
    if (ioctl(fd, FIBMAP, &block)) {
      perror("FIBMAP ioctl failed");
    
	}

	if(pba[block-1000] == -1) pba[block-1000] = 4294967295;
    fprintf(output, "%d %ld\n", block,pba[block-1000]);
  }

  end:
  fprintf(output, "END\n");
  close(fd);
}


void extract_pba(){

	int fd = open("/sys/kernel/debug/tracing/trace", O_RDONLY);
//	int fd = open("sample", O_RDONLY);	
	int idx = 0;
	char buf [100];
	int line = 1, cell = 0;
	int i = 0;
	if(fd == -1) printf("FD error\n");

	while(read(fd, buf+idx, 1)){
			
		if(buf[idx]=='\n'){//newline
		
			if(line > 11){
				*(buf + idx + 1) = '\0';
				//printf("num %d:%s", line, buf + 65);
								
				char* ptr = strtok(buf + 65, " ");
				ptr = strtok(NULL," ");
				pba[i] = atoi(ptr);
				i++;		
				idx = 0;
				line++;

			}
			else{
				*(buf + idx + 1) = '\0';
				idx = 0;
				line++;
			}
		}	

		else{
			idx++;
		}

	}

}
