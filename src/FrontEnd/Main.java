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
