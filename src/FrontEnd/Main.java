package FrontEnd;

import java.io.*;
import java.util.*;

public class Main
{

   public static void main(String[] args)
   {
      ArrayList<CellNucleus> nuclei;

      if (args.length != 1)
      {
         System.out.println("please provide data file");
         return;
      }
      
      nuclei = fillMatrix(args[0]);

      if (nuclei == null)
      {
         System.out.println("Error reading data");
         return;
      }

      for (CellNucleus nucleus : nuclei)
      {
         System.out.println(nucleus);
      }
      
      double[][] data = getDataArray(nuclei);
      boolean[] classes = getClasses(nuclei);
      
      
   }

   public static double[][] getDataArray(ArrayList<CellNucleus> nuclei) {
	   double[][] ret = new double[nuclei.size()][30];
	   for (int i=0; i<ret.length; i++) ret[i] = nuclei.get(i).getFeatureArray();
	   return ret;
   }
   
   // Standardizes the data by subtracting the mean of each column from each data point in that column
   // Then dividing by the mean of that column.
   public void standardizeData (double[][] data) {
	   assert (data.length>0);
	   double[] avgs = new double[data[0].length];
	   for (int i = 0; i < data.length; i++) {
		   for (int j = 0; j < data[i].length; j++) {
			   avgs[j] += data[i][j];
		   }
	   }
	   
	   for (int i = 0; i < avgs.length; i++) {
		   avgs[i] = avgs[i]/data.length;
	   }
	   
	   for (int i = 0; i < data.length; i++) {
		   for (int j = 0; j < data[i].length; j++) {
			   data[i][j] = (data[i][j]-avgs[j])/avgs[j];
		   }
	   }
   }
   
   public static boolean[] getClasses(ArrayList<CellNucleus> nuclei) {
	   boolean[] ret = new boolean[nuclei.size()];
	   for (int i=0; i<ret.length; i++) ret[i] = nuclei.get(i).isBenign();
	   return ret;
   }
   
   private static ArrayList<CellNucleus> fillMatrix(String fileName)
   {
      File file;
      Scanner scanner;
      String line;
      CellNucleus newCell;
      ArrayList<CellNucleus> cells = new ArrayList<CellNucleus>();

      try
      {
         file = new File(fileName);
         scanner = new Scanner(file);
      }
      catch (FileNotFoundException e)
      {
         return null;
      }

      // skip column header row
      if (scanner.hasNextLine())
      {
         scanner.nextLine();
      }

      while (scanner.hasNextLine())
      {
         line = scanner.nextLine();
         newCell = createCellFromString(line);
         cells.add(newCell);
      }

      scanner.close();

      return cells;
   }

   private static CellNucleus createCellFromString(String line)
   {
      int id;
      boolean benign;
      ArrayList<Double> features;
      String splitLine[];
      CellNucleus cell;

      splitLine = line.split(",");

      // parse id
      id = Integer.parseInt(splitLine[0]);

      // parse categorization
      benign = splitLine[1].equals("B");

      // parse features
      features = new ArrayList<Double>();
      for (int i = 2; i < splitLine.length; i++)
      {
         features.add(Double.parseDouble(splitLine[i]));
      }

      cell = new CellNucleus(id, benign, features);

      return cell;
   }

}
