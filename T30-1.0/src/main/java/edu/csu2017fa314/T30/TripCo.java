package edu.csu2017fa314.T30;
import edu.csu2017fa314.T30.Model.Model;
import java.util.Arrays;

public class TripCo
{

   private String name = "";

   public String getName()
   {
      return name;
   }

   public String getMessage()
   {
      if (name == "")
      {
         return "Hello!";
      }
      else
      {
         return "Hello " + name + "!";
      }
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public static void main(String[] args) {
      Model model = new Model();
      model.readData();
      String[][] itin = model.buildItinerary();
      for(int i = 0; i < itin.length; i++){
         System.out.println(Arrays.toString(itin[i]));
      }
   }

}
