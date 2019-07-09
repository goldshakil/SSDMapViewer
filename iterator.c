// Iterator.c ver 2.0.0
#include <sys/type.h>
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

//Directory Looping Function
static int ptree(char *curpath, char * const path);
//LBA Extraction Function
void fibmap_fun(char name[20]);
    
      int main(int argc, char * const argv[])
      {
              int k;
              int rval;
    
              for (rval = 0, k = 1; k < argc; k++)
                      if (ptree(NULL, argv[k]) != 0)
                              rval = 1;
              return rval;
      }
    
      static int
      ptree(char *curpath, char * const path)
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
                      if (S_ISREG(st.st_mode) || S_ISDIR(st.st_mode)) {
                              printf("%c %s\n", S_ISDIR(st.st_mode) ? 'd' : 'f', ep);
							char new_name[25];

							int counter=2;
							int counter2=0;
							while(ep[counter]!='\0')
							{
								new_name[counter2]=ep[counter];
								counter2++;
								counter++;
							}

							new_name[counter2]='\0';


						  fibmap_fun(new_name);
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
	printf("File %s size %d blocks %d blocksize %d\n",
			file_name, (int)st.st_size, blkcnt, blocksize);

	for (i = 0; i < blkcnt; i++) {
		block = i;
		if (ioctl(fd, FIBMAP, &block)) {
			perror("FIBMAP ioctl failed");
		}
		printf("%3d %10d\n", i, block);
	}

end:
	close(fd);
}
