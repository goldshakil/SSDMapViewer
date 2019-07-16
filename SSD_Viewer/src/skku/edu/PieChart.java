package skku.edu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PieChart extends JFrame {
    private JTextArea textArea1;
    private JPanel panel1;
    private JButton backBtn;

    public PieChart(String file_name) throws IOException {
        textArea1.setText(file_name);
        add(panel1);
        setTitle("Mapping Table");
        setSize(400,500);

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setVisible(false);

                SSDGUIFORM ssdForm = null;
                try {
                    ssdForm = new SSDGUIFORM();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                ssdForm.setVisible(true);
            }
        });
    }

}
