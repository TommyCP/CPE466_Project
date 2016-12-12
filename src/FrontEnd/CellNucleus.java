package FrontEnd;

import java.util.*;

public class CellNucleus
{
   private int id;
   private boolean benign;
   private ArrayList<Double> features;

   public CellNucleus(int id, ArrayList<Double> features)
   {
      this.id = id;
      this.features = features;
   }

   public CellNucleus(int id, boolean benign, ArrayList<Double> features)
   {
      this.id = id;
      this.benign = benign;
      this.features = features;
   }

   public boolean isBenign()
   {
      return benign;
   }

   public void setBenign(boolean benign)
   {
      this.benign = benign;
   }

   public int getId()
   {
      return id;
   }

   public ArrayList<Double> getFeatures()
   {
      return features;
   }
   
   public double[] getFeatureArray()
   {
	   double[] ret = new double[features.size()];
	   for (int i=0; i<ret.length; i++) ret[i]=features.get(i);
	   return ret;
   }

   public String toString()
   {
      return "id: " + id + "   benign: " + benign + "   features: " + features;
   }
}
