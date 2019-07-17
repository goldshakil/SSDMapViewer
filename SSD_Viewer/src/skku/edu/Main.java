package skku.edu;


import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
	// write your code here

        //SSDGUIFORM myGUIFORM=new SSDGUIFORM();
        //myGUIFORM.setVisible(true);

        //MappingTable myMapping = new MappingTable();
        //myMapping.setVisible(true);

        //PBAtable myPBA = new PBAtable("file1");
        //myPBA.setVisible(true);

        PBA_distribution mydistribution = new PBA_distribution("file1");
        mydistribution.setVisible(true);

    }
}
