package skku.edu;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Vector;

public class SSDGUIFORM extends JFrame{
    private JButton tableBtn;
    private JPanel rootPanel;
    private JList list1;
    private JScrollPane scrPane;
    private JButton pieBtn;
    Vector<String> filenames = new Vector<String>();
    String file_name="";

    public SSDGUIFORM() throws IOException {


        /*File I/O*/
        File file = new File("//Users//softhoon//Desktop//file.txt"); //file path .. please change according to your input file

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
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(rootPanel);
        setTitle("This is SSD Viewer");
        setSize(400,500);

        JListHandler handler = new JListHandler();
        list1.addListSelectionListener(handler);

        tableBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Log button "+file_name);

                if(file_name.length() > 0){
                    System.out.println("Log legal "+file_name);

                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setVisible(false);

                    MappingTable myMapping = null;
                    try {
                        myMapping = new MappingTable(file_name);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    myMapping.setVisible(true);
                }
            }
        });

        pieBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Log pie button "+file_name);

                if(file_name.length() > 0){
                    System.out.println("Log pie legal "+file_name);

                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setVisible(false);

                    PieChart myPie = null;
                    try {
                        myPie = new PieChart(file_name);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    myPie.setVisible(true);
                }
            }
        });

    }

    private class JListHandler implements ListSelectionListener
    {
        //when list item is selected
        public void valueChanged(ListSelectionEvent event)
        {
            file_name = (String)list1.getSelectedValue();
            System.out.println("Log "+file_name);
        }
    }
}