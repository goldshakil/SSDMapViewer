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
    private JButton distributionBtn;
    private JTextField fileTextField;
    Vector<String> filenames = new Vector<String>();
    String file_name="";
    String Path = "";

    public SSDGUIFORM() throws IOException, ClassNotFoundException {

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
        setSize(500,500);
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
                        myPBAtable = new PBAtable(Path, file_name);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
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
                        myPie = new PieChart(Path, file_name);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    myPie.setVisible(true);
                }
            }
        });
        distributionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Log distribution button "+file_name);

                if(file_name.length() > 0){
                    System.out.println("Log distribution legal "+file_name);

                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setVisible(false);

                    PBA_distribution mydistribution = null;
                    try {
                        mydistribution = new PBA_distribution(Path, file_name);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    mydistribution.setVisible(true);
                }
            }
        });

    }



    public SSDGUIFORM(String path) throws IOException {

        System.out.println("path "+path);
        if(path.length() > 0){
            Path = path;
            fileNameLabel.setText(Path);
            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            int ack = 0;
            while ((st = br.readLine()) != null)
            {
                if(st.equals("END")) break;

                    filenames.addElement(st);

                }
            list1.setListData(filenames);
            list1.clearSelection();
            }

        /*Setting the data to our list*/
        list1.setSelectedIndex(0);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list1.clearSelection();
        add(rootPanel);
        setTitle("This is SSD Viewer");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JListHandler handler = new JListHandler();
        list1.addListSelectionListener(handler);

        fileOpenBtn.addActionListener(e -> {
            list1.setListData(selectFile(filenames));
        });
        //pack();
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
                        myPBAtable = new PBAtable(Path, file_name);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
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
                        myPie = new PieChart(Path, file_name);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    myPie.setVisible(true);
                }
            }
        });
        distributionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Log distribution button "+file_name);

                if(file_name.length() > 0){
                    System.out.println("Log distribution button legal "+file_name);

                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setVisible(false);

                    PBA_distribution mydistribution = null;
                    try {
                        mydistribution = new PBA_distribution(Path, file_name);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    mydistribution.setVisible(true);
                }
            }
        });

    }

    public Vector<String> selectFile(Vector<String> fileList) {

        fileList.clear();

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
                Path =f.getAbsolutePath();
                fileNameLabel.setText(Path);
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