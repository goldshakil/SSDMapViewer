package skku.edu;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PieChart extends JFrame {

    private JPanel panel1;
    private JLabel label;
    private JButton backBtn;
    private JPanel graphPanel;

    public PieChart(String file_name) throws IOException {



        label.setText(" "+file_name);
       setContentPane(panel1);
       this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000,700);
        setTitle("Pie Chart");
        setVisible(true);

        /*Chart ..*/
        DefaultPieDataset dataSet=new DefaultPieDataset();
        dataSet.setValue("a",20.0);
        dataSet.setValue("b",2.5);
        JFreeChart pieChart= ChartFactory.createPieChart("PBA",dataSet);


        ChartFrame chartFrame=new ChartFrame("PBA",pieChart,true);
       // chartFrame.setVisible(true);
        chartFrame.setSize(500,400);
        ChartPanel chartPanel=new ChartPanel(pieChart);
        graphPanel.removeAll();
        graphPanel.add(chartPanel);
        graphPanel.updateUI();


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
