package skku.edu;

import javax.swing.*;
import java.io.*;
import java.util.Vector;

public class SSDGUIFORM extends JFrame
{
    private JButton Mybutton;
    private JPanel rootPanel;
    private JList list1;
    private JScrollPane scrPane;
    Vector<String> filenames = new Vector<String>();

    public SSDGUIFORM() throws IOException {


        /*File I/O*/
        File file = new File("C:\\Users\\Dahab Shakeel\\Desktop\\SSD_Viewer\\src\\skku\\edu\\filenames.txt"); //file path .. please change according to your input file

        BufferedReader br = new BufferedReader(new FileReader(file));

        /*Saving the file names*/
        String st;
        while ((st = br.readLine()) != null)
        {
            filenames.addElement(st);
        }

        /*Setting the data to our list*/
        list1.setListData(filenames);
        list1.setSelectedIndex(0);

        add(rootPanel);
        setTitle("This is SSD Viewer");
        setSize(400,500);
    }
}
