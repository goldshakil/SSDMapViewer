//version 1.0.0
package skku.edu;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PieChart extends JFrame {

    private JPanel panel1;
    private JLabel label;
    private JButton backBtn;
    private JPanel graphPanel;
    Vector<String> LBA_vec = new Vector<String>();
    Vector<String> PBA_vec = new Vector<String>();
    Vector<Integer>PBA_vec_converted=new Vector<Integer>();
    //for storing ranges
    //<Integer, Integer> ranges = new HashMap<>();

    public PieChart(String Path, String file_name) throws IOException {



        label.setText(" "+file_name);
       setContentPane(panel1);
       this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000,700);
        setTitle("Internal fragmentation");
        setVisible(true);

        /*Reading */
        File file = new File(Path); //file path .. please change according to your input file
        BufferedReader br = new BufferedReader(new FileReader(file));

        //reading from a buffer into a string
        String st;
        int ack = 0;
        while ((st = br.readLine()) != null)
        {
            if(st.equals("END")) ack = 0;

            if(ack == 1){

                //TODO: LBA_vec, PBA_vec push back


                LBA_vec.addElement(st.split("\\s")[0]);
                PBA_vec.addElement(st.split("\\s")[2]);
            }
            if(st.equals(file_name+":")) ack = 1;
        }

        /*Convert the String Vector to Integer*/
        int size=PBA_vec.size();
       for(int i=0;i<size;i++)
       {
           PBA_vec_converted.addElement(Integer.parseInt(PBA_vec.get(i)));
       }


       /*Sorting and finding the maximum (element at the last index(size-1))*/
        Collections.sort(PBA_vec_converted);


        /*find number of ranges+seperate ranges*/
        int[][] ranges = new int[size][2];//at max there will be "size" number of elements
        int array_counter=0;//for looping through the array

        int groups=0;//number of groups
        int range_numbers=0;//number of elements within a group
        for(int i=0;i<size;i++)
        {
            range_numbers++;
            if(i==size-1)
            {
                System.out.println(PBA_vec_converted.get(i));
                System.out.println(range_numbers);
                ranges[array_counter][0]=PBA_vec_converted.get(i)-range_numbers+1; //start
                ranges[array_counter][1]=PBA_vec_converted.get(i); //end

                break;
            }

          else if(PBA_vec_converted.get(i)+1!=PBA_vec_converted.get(i+1))
          {
              System.out.println(PBA_vec_converted.get(i));
              System.out.println(range_numbers);
              ranges[array_counter][0]=PBA_vec_converted.get(i)-range_numbers+1; //start
              ranges[array_counter][1]=PBA_vec_converted.get(i); //end
              range_numbers=0;
              groups++;
              array_counter++;
          }
        }
        groups++;//number of groups=number of separations+1

        for(int j=0;j<groups;j++)
        {
            System.out.printf("%d \t %d\n",ranges[j][0],ranges[j][1]);
        }
        //smallest value in the ranges is ranges[0][0] -> subtract from all
        /*add values to the chart*/
        DefaultPieDataset dataSet=new DefaultPieDataset();
        for(int j=0;j<groups;j++)
        {
            dataSet.setValue(Integer.toString(ranges[j][1]),ranges[j][1]-ranges[j][0]+1);//adding the range segment
            //adding the gap segment
            dataSet.setValue("gap"+Integer.toString(j),ranges[j+1][0]-ranges[j][1]+1);


        }
        /*Color the chart to black and red*/
        JFreeChart pieChart= ChartFactory.createPieChart("Internal PBA fragmentation for "+file_name,dataSet);

        PiePlot plot = (PiePlot) pieChart.getPlot();
        for(int j=0;j<groups;j++)
        {
            plot.setSectionPaint(Integer.toString(ranges[j][1]), Color.RED);
            //adding the gap segment
            plot.setSectionPaint("gap"+Integer.toString(j), Color.BLACK);
        }



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
                    ssdForm = new SSDGUIFORM(Path);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                ssdForm.setVisible(true);
            }
        });
    }



}
