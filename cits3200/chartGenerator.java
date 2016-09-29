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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.RangeType;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class chartGenerator extends ApplicationFrame
{
	static int max;
	public chartGenerator( String applicationTitle, String chartTitle )
   {
      super(applicationTitle);
      JFreeChart xylineChart = ChartFactory.createXYLineChart(
    	         chartTitle ,
    	         "Time" ,
    	         "Temperature" ,
    	         createDataset() ,
    	         PlotOrientation.VERTICAL ,
    	         true , true , false);

      ChartPanel chartPanel = new ChartPanel( xylineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      final XYPlot plot = xylineChart.getXYPlot( );
      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
      renderer.setSeriesPaint( 0 , Color.RED );
      renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
      plot.setRenderer( renderer );
      plot.setDomainPannable(true);
      setContentPane( chartPanel );
      
   }

   private static XYDataset createDataset(){
	     final XYSeries sheep = new XYSeries("Sheep");

	     File file = new File("data.csv");
	     FileInputStream fis = null;
	     BufferedInputStream bis = null;
	     DataInputStream dis = null;
	     try{
	       fis = new FileInputStream(file);
	       bis = new BufferedInputStream(fis);
	       dis = new DataInputStream(bis);
	       dis.readLine();
	       int i =0;
	       while(dis.available() != 0){
	         String line = dis.readLine();
	         String[] parts = line.split(",");
	         System.out.println(i);
	         System.out.println(parts[1]);
	         sheep.add(i,Double.parseDouble(parts[1]));
	         i++;
	       }
	       max = i;
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
	     final XYSeriesCollection dataset = new XYSeriesCollection( );
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
