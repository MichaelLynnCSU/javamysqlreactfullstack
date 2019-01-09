package edu.csu2017fa314.T30;
import edu.csu2017fa314.T30.Model.Model;
import edu.csu2017fa314.T30.View.View;
import edu.csu2017fa314.T30.Model.Server;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;


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
      View view = new View();

      System.out.print("enter filepath to web directory: ");
      Scanner sc = new Scanner(System.in);
      String web_filepath = sc.nextLine();
      view.setWebPath(web_filepath);

      Server s = new Server();
      s.serve();

   }

}
