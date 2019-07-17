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

    String file_name="";

    public PBA_distribution(String file_name) throws IOException{

        distribution_label.setText(" PBA distribution:  " + file_name);
        userColumn.addElement("PBA");
        userColumn.addElement("Allocation");
        model = new DefaultTableModel(userColumn, 0);


        for(int i = 0; i < 1500; i++) {
            Vector<String> userRow = new Vector<>();
            userRow.addElement(Integer.toString(i));
            userRow.addElement(" ");
            model.addRow(userRow);
        }
        distribution_table = new JTable(model);
        jscrollpane.setViewportView(distribution_table);

        File file = new File("C:\\Users\\rudob\\IdeaProjects\\SSDViewerGUI\\SSD_Viewer\\src\\skku\\edu\\filenames.txt"); //file path .. please change according to your input file
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        int ack = 0;
        while ((st = br.readLine()) != null)
        {
            if(st.equals("END")) ack = 0;

            if(ack == 1){
                System.out.println(st.split("\\s")[2]);

                distribution_table.setValueAt("//////////////////////////////////////",Integer.parseInt(st.split("\\s")[2]), 1);

            }
            if(st.equals(file_name+":")) ack = 1;
        }







        //TODO: change the range into real PBA range

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