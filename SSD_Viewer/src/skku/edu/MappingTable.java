package skku.edu;

import javax.swing.*;
import java.io.IOException;

public class MappingTable extends JFrame {
    private JPanel panel1;
    private JTextArea textArea1;

    public MappingTable(String file_name) throws IOException {

        textArea1.setText(file_name);
        add(panel1);
        setTitle("Mapping Table");
        setSize(400,500);
    }
}
