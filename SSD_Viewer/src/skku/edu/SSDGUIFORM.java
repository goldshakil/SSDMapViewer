package skku.edu;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Vector;

public class SSDGUIFORM extends JFrame
{
    private JButton Mybutton;
    private JPanel rootPanel;
    private JList list1;
    private JScrollPane scrPane;
    private Vector fileInfo;
    private Random random;
    private String[] fileName = {"1.txt","2.txt","3.txt","4.txt","5.txt"};

    public SSDGUIFORM()
    {
        random = new Random();
        fileInfo = new Vector();

        for (int i = 0; i < 1000; i++) {
            int random_varible = random.nextInt(5);
        //    fileInfo.addElement(file[random_varible]);
        }

        list1.setListData(fileInfo);
        list1.setSelectedIndex(0);

        add(rootPanel);
        setTitle("This is SSD Viewer");
        setSize(400,500);
    }
}
