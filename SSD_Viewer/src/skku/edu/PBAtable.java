package skku.edu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;


public class PBAtable extends JFrame{
    private JButton back_button;
    private JPanel rootPanel;
    private JList LBA_list;
    private JLabel label;
    private JList PBA_list;
    private JScrollPane scrPane;
    Vector<String> LBA_vec = new Vector<String>();
    Vector<String> PBA_vec = new Vector<String>();
    String file_name="";

    public PBAtable(String file_name) throws IOException {


        label.setText(" "+file_name);
        /*File I/O*/
        File file = new File("/Users/softhoon/Desktop/Java/SSDViewerGUI/SSD_Viewer/src/skku/edu/filenames.txt"); //file path .. please change according to your input file

        BufferedReader br = new BufferedReader(new FileReader(file));

        /*Saving the file names*/

        String st;
        int ack = 0;
        while ((st = br.readLine()) != null)
        {
            if(st.equals("END")) ack = 0;

            if(ack == 1){

                System.out.println(st);
                LBA_vec.addElement(st.split("\\s")[0]);
                PBA_vec.addElement(st.split("\\s")[2]);
            }
            if(st.equals(file_name+":")) ack = 1;
        }



        /*Setting the data to our list*/
        LBA_list.setListData(LBA_vec);
        LBA_list.setSelectedIndex(0);
        PBA_list.setListData(PBA_vec);
        LBA_list.clearSelection();
        PBA_list.clearSelection();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        add(rootPanel);
        setTitle("LBA PBA Table");
        setSize(400,500);


        back_button.setPreferredSize(new Dimension(10,10));


        back_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Log button "+file_name);

                if(file_name.length() > 0){
                    System.out.println("Log legal "+file_name);

                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setVisible(false);



                    SSDGUIFORM myGUIFORM = null;
                    try {
                        myGUIFORM = new SSDGUIFORM();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    myGUIFORM.setVisible(true);
                }
            }
        });
    }
}