package skku.edu;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.util.Vector;

public class SSDGUIFORM extends JFrame{
    private JButton tableBtn;
    private JPanel rootPanel;
    private JList list1;
    private JScrollPane scrPane;
    private JButton pieBtn;
    private JButton fileOpenBtn;
    private JLabel fileNameLabel;
    private JTextField fileTextField;
    Vector<String> filenames = new Vector<String>();
    String file_name="";

    public SSDGUIFORM() throws IOException {






        //File file = new File("/Users/softhoon/Desktop/Java/SSDViewerGUI/SSD_Viewer/src/skku/edu/filenames.txt");

        //BufferedReader br = new BufferedReader(new FileReader(file));

        /*Saving the file names*/
//        String st;
//
//        while ((st = br.readLine()) != null)
//        {
//            if(st.equals("END")) break;
//            filenames.addElement(st);
//        }

        fileOpenBtn.addActionListener(e -> {
            list1.setListData(selectFile(filenames));
        });
        pack();

        /*Setting the data to our list*/
        list1.setSelectedIndex(0);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list1.clearSelection();
        add(rootPanel);
        setTitle("This is SSD Viewer");
        setSize(400,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

                    PBAtable myPBAtable = null;
                    try {
                        myPBAtable = new PBAtable(file_name);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    myPBAtable.setVisible(true);
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

    public Vector<String> selectFile(Vector<String> fileList) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES","txt");
        chooser.setFileFilter(filter);
        // optionally set chooser options ...
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            File file = new File(f.getAbsolutePath());
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
            String st;
                while ((st = bufferedReader.readLine()) != null) {
                    if (st.equals("END")) break;
                    fileList.addElement(st);
                }
                fileNameLabel.setText(f.getAbsolutePath());
            }catch (Exception o){

            }

            //System.out.println(f.getAbsolutePath());
        } else {
            // user changed their mind
        }
        return fileList;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private class JListHandler implements ListSelectionListener
    {
        //when list item is selected
        @Override
        public void valueChanged(ListSelectionEvent event)
        {
            System.out.println(event);
            if(list1.getSelectedValue() != null){
                file_name = list1.getSelectedValue().toString();
                System.out.println("Log "+file_name);
            }
        }
    }
}