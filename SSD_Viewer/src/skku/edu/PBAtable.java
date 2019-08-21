package skku.edu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
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
    private JLabel label;
    private JTable mapping_table;
    private JScrollPane jscrollpane;
    Vector<String> userColumn = new Vector<>();
    DefaultTableModel model;

    String file_name="";

    public PBAtable(String Path, String file_name) throws IOException, ClassNotFoundException {

        System.out.println("pba file name: "+file_name);

        label.setText("\t"+file_name);
        userColumn.addElement("LBA");
        userColumn.addElement("PBA");
        model = new DefaultTableModel(userColumn, 0);

        /*File I/O*/
        File file = new File(Path); //file path .. please change according to your input file
        BufferedReader br = new BufferedReader(new FileReader(file));

        /*Saving the file names*/
        String st;
        int ack = 0;
        while ((st = br.readLine()) != null)
        {
            if(st.equals("END")){
                if(ack==2) break;
                else ack = 1;
            }
            if(ack == 2){
                if(!st.split("\t")[1].equals("4294967295")) {
                    Vector<String> userRow = new Vector<>();
                    System.out.println(">"+st.split("\t")[0]);
                    System.out.println(">"+st.split("\t")[1]);

                    userRow.addElement(st.split("\t")[0]);
                    userRow.addElement(st.split("\t")[1]);
                    model.addRow(userRow);
                }
            }
            if(st.equals(file_name)&&ack==1) ack = 2;
        }

        mapping_table = new JTable(model);
        jscrollpane.setViewportView(mapping_table);

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
                        myGUIFORM = new SSDGUIFORM(Path);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    myGUIFORM.setVisible(true);
                }
            }
        });
    }
}