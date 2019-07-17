package skku.edu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class PBA_distribution extends JFrame{
    private JPanel rootPanel;
    private JTable distribution_table;
    private JLabel distribution_label;
    private JButton back_button;
    private JScrollPane jscrollpane;
    Vector<String> userColumn = new Vector<>();
    DefaultTableModel model;
    Vector<String> userRow = new Vector<>();
    String file_name="";

    public PBA_distribution(String file_name) throws IOException{

        distribution_label.setText(" PBA distribution:  " + file_name);
        File file = new File("C:\\Users\\rudob\\IdeaProjects\\SSDViewerGUI\\SSD_Viewer\\src\\skku\\edu\\filenames.txt"); //file path .. please change according to your input file
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        int ack = 0;
        while ((st = br.readLine()) != null)
        {
            if(st.equals("END")) ack = 0;

            if(ack == 1){
                System.out.println(st.split("\\s")[2]);

            }
            if(st.equals(file_name+":")) ack = 1;
        }


        userColumn.addElement("PBA");
        userColumn.addElement("Allocation");
        model = new DefaultTableModel(userColumn,0);
        distribution_table = new JTable(model);

        userRow.addElement("1");
        userRow.addElement("one");
        model.addRow(userRow);

        //TODO: change the range into real PBA range


        jscrollpane.setViewportView(distribution_table);
        add(rootPanel);
        setTitle("PBA Distribution");
        setSize(400,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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