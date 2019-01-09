package edu.csu2017fa314.T30.Model;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.lang.Math;

public class Model
{
   private int[] numbers;
   private ArrayList<String[]> locations;
   private String d = "";

   public Model ()
   {
      numbers = new int[] {0, 1, 2, 3, 4, 5};
      locations = new ArrayList<>();
   }

   /*
      readData takes the csv filepath from input, then reads it into this.locations
      in the format [ID, Name, City, Latitude, Longitude, ElevationFt]

      Improperly formatted lines in the file are skipped, however it does not check the
      format of the latitude and longitude columns
   */
   public void readData(){
      System.out.print("csv filepath: ");
      Scanner sc = new Scanner(System.in);
      String csv_filepath = sc.nextLine();
      try (BufferedReader read = new BufferedReader(new FileReader(csv_filepath))) {
         String[] data;
         HashMap<String,Integer> arr_locations = new HashMap<String, Integer>();
         d = read.readLine();
         data = d.split(",");
         for (int i = 0; i < data.length; i++){
            arr_locations.put(data[i].trim().toLowerCase(),i);
         }
         while ((d = read.readLine()) != null) {
            String[] brewery = new String[6];
            data = d.split(",");
            try {
               brewery[0] = data[arr_locations.get("id")].trim();
               brewery[1] = data[arr_locations.get("name")].trim();
               //brewery[2] = data[arr_locations.get("city")].trim();
               brewery[2] = data[arr_locations.get("latitude")].trim();
               brewery[3] = data[arr_locations.get("longitude")].trim();
               //brewery[5] = data[arr_locations.get("elevation")].trim();
               this.locations.add(brewery);
            } catch (java.lang.ArrayIndexOutOfBoundsException e){ }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public int[] getNumbers() 
   {
      return numbers;
   }
   
   
   	public static double stringToDecimal(String degree) { 
		double decimal;
		if (degree.indexOf("\"") != -1) { //Check for seconds
	    	String[] strArray=degree.split("[°\"']");
	    	decimal = Double.parseDouble(strArray[0])+Double.parseDouble(strArray[1])/60+Double.parseDouble(strArray[2])/3600;
		}
		else if (degree.indexOf("\'") != -1) { //Check for minutes
	    	String[] strArray=degree.split("[°\"']");
	    	decimal = Double.parseDouble(strArray[0])+Double.parseDouble(strArray[1])/60;
	    }
		else {
			String[] strArray=degree.split("[°\"']");
			decimal = Double.parseDouble(strArray[0]);
		}
		if ((degree.toLowerCase().indexOf("s") != -1) || degree.toLowerCase().indexOf("w") != -1)
			decimal = (0 - decimal); //South and West are considered negative in decimal form
		return decimal;
	    	
	}

  
	
	
	public static double computeGCD (String dx1, String dy1, String dx2, String dy2) {
		return computeGCD(stringToDecimal(dx1), stringToDecimal(dy1), stringToDecimal(dx2), stringToDecimal(dy2));
	}
	
	public static double computeGCD (double dx1, double dy1, double dx2, double dy2) {
		//Assumes x1, y1, etc are degrees for latitude/longitude
		double earthMI = 3958.7613;
		double x1 = Math.toRadians(dx1);
		double y1 = Math.toRadians(dy1);
		double x2 = Math.toRadians(dx2);
		double y2 = Math.toRadians(dy2);
		
		double top = Math.sqrt( Math.pow(Math.cos(x2) * Math.sin(y2-y1), 2) + 
							Math.pow((Math.cos(x1) * Math.sin(x2) - Math.sin(x1)*Math.cos(x2)*Math.cos(y2 - y1)), 2));
		double bottom = (Math.sin(x1) * Math.sin(x2)) + (Math.cos(x1) * Math.cos(x2) * Math.cos(y2 - y1));
		
		double tan = Math.atan2(top, bottom);
		double dist = tan * earthMI;
		
		return dist;
	}
	
	public String[][] buildItinerary () {
		String[][] itinerary = new String[locations.size() - 1][3];
		for (int i = 0; i < locations.size() - 1; i++) {
			itinerary[i][0] = locations.get(i)[1];
			itinerary[i][1] = locations.get(i+1)[1];
			itinerary[i][2] = Double.toString(computeGCD(locations.get(i)[2], locations.get(i)[3], locations.get(i+1)[2], locations.get(i+1)[3]));
		}
		return itinerary;
	}
   
   
}
