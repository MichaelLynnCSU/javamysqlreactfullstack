package edu.csu2017fa314.T30;
import edu.csu2017fa314.T30.Model.Model;
import edu.csu2017fa314.T30.View.View;


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
      View view = new View();
      model.readData();
      view.buildMap(model.shortestItinerary());
   }

}
