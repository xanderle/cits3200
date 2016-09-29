package cits3200;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.BasicStroke;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.RangeType;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.data.time.*;
public class chartGenerator extends ApplicationFrame
{
	public chartGenerator( String applicationTitle, String chartTitle )
   {
      super(applicationTitle);
      JFreeChart xylineChart = ChartFactory.createTimeSeriesChart(
    	         chartTitle ,
    	         "Time" ,
    	         "Temperature" ,
    	         createDataset() ,
    	         true , true , false);

      ChartPanel chartPanel = new ChartPanel( xylineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 1920 , 1080 ) );

      final XYPlot plot = xylineChart.getXYPlot( );
     
      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
      renderer.setSeriesPaint( 0 , Color.RED );
      renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
      DateAxis dateAxis = (DateAxis)plot.getDomainAxis();
      dateAxis.setDateFormatOverride(new SimpleDateFormat("DD/MM/YYYY HH:mm"));
      
      plot.setRenderer( renderer );
      plot.setDomainPannable(true);
      setContentPane( chartPanel );      
   }


   private static XYDataset createDataset(){
	     final TimeSeries sheep = new TimeSeries("Sheep");

	     File file = new File("data.csv");
	     FileInputStream fis = null;
	     BufferedInputStream bis = null;
	     DataInputStream dis = null;
	     try{
	       fis = new FileInputStream(file);
	       bis = new BufferedInputStream(fis);
	       dis = new DataInputStream(bis);
	       dis.readLine();
	       int day;
	       int month;
	       int year;
	       int hour;
	       int minute;
	       String[] parts;
	       String[] dateTime;
	       String dateString;
	       String timeString;
	       String[] date;
	       String[] time;
	       
	       while(dis.available() != 0){
	         String line = dis.readLine();
	         parts = line.split(",");
	         System.out.println("Parts is "+parts[0]+" "+parts[1]);
	         dateTime= parts[0].split(" ");
	         System.out.println("Date "+dateTime[0]+" Time "+dateTime[1]);
	         dateString = dateTime[0];
	         timeString = dateTime[1];
	         date = dateString.split("/");
	         time = timeString.split(":");
	         day = Integer.parseInt(date[0]);
	         month = Integer.parseInt(date[1]);
	         year = Integer.parseInt(date[2]);
	         hour = Integer.parseInt(time[0]);
	         minute = Integer.parseInt(time[1]);
	         System.out.println(day);
	         System.out.println(month);
	         System.out.println(year);
	         System.out.println(hour);
	         System.out.println(minute);
	         sheep.add(new Minute(minute,hour,day,month,year),Double.parseDouble(parts[1]));
	       }
	       fis.close();
	       bis.close();
	       dis.close();
	     }

	     catch(FileNotFoundException e){
	       e.printStackTrace();
	     }
	     catch(IOException e){
	       e.printStackTrace();
	     }
	     final TimeSeriesCollection dataset = new TimeSeriesCollection( );
	     dataset.addSeries(sheep);
	     return dataset;
	   }

   
   public static void main( String[ ] args )
   {
	  
      chartGenerator chart = new chartGenerator("Temperature Analysis", "Temperature Analysis");
      chart.pack( );
      RefineryUtilities.centerFrameOnScreen( chart );
      chart.setVisible( true );
      
      
   }
}
