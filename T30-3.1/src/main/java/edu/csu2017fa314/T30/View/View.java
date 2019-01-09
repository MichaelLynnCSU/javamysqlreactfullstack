package edu.csu2017fa314.T30.View;
import edu.csu2017fa314.T30.Model.Location;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.apache.commons.io.FileUtils;
import java.lang.Math;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public class View {
	private int totalDistance;
	private String webPath;

	// create construct that take itr, josn and keys

	public int getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(String[][] itinerary) {
		for (int i = 0; i < itinerary.length; i++) {
			totalDistance  += Math.round(Double.parseDouble(itinerary[i][2]));
		}

	}

	public void setWebPath(String path){
		this.webPath = path;
	}


	public int getIternaryLocation(String iteneraryName, ArrayList<TreeMap<String,String>> jsonData) {

		int location = 0;
		for (int j = 0; j < jsonData.size(); j++) {
			if (iteneraryName == jsonData.get(j).get("name")) {
				location = j;
			}
		}
		return  location;
	}

	static double[]  degree2SVG (double latitude, double longitude) {
		double[] ULs = {38, 38}; 	double[] URs = {1028, 38};  //The four corners of the SVG Colorado map
		double[] BLs = {38, 745}; 	double[] BRs = {1028, 745};
		double[] ULd = {41, -109};  	double[] URd = {41, -102}; // The four corners of the map in degrees
		double[] BLd = {37, -109};  	double[] BRd = {37, -102};
		double widthRatio = (URs[0] - ULs[0]) / (URd[1] - ULd[1]);
		double heightRatio = (BLs[1] - ULs[1]) /(BLd[0] - ULd[0]);
		double[] x = {ULs[0] + (ULd[1] - longitude) * (-widthRatio), ULs[1] + (ULd[0] - latitude) * (-heightRatio)};
		return x;
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

	public void buildMap(String[][] itinerary) {
		try{
			File source = new File("./web/USA_Colorado_location_map.svg");
			File target = new File("./web/itin.svg");
			FileUtils.copyFile(source, target);
			FileWriter fw = new FileWriter("./web/itin.svg", true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter writer = new PrintWriter(bw);
			//writer.println("<?xml version=\"1.0\"?>");
			//writer.println("<svg width=\"1280\" height=\"1024\" xmlns=\"http://www.w3.org/2000/svg\">");
			writer.println("<g>");
			writer.println("<title>Borders</title>");
			writer.println("<line id=\"north\" y2=\"38\" x2=\"1028\" y1=\"38\" x1=\"38\" stroke-width=\"5\" stroke=\"#666666\"/>");
			writer.println("<line id=\"east\" y2=\"745\" x2=\"1028\" y1=\"38\" x1=\"1028\" stroke-width=\"5\" stroke=\"#666666\"/>");
			writer.println("<line id=\"south\" y2=\"745\" x2=\"38\" y1=\"745\" x1=\"1028\" stroke-width=\"5\" stroke=\"#666666\"/>");
			writer.println("<line id=\"west\" y2=\"38\" x2=\"38\" y1=\"745\" x1=\"38\" stroke-width=\"5\" stroke=\"#666666\"/>");
			for (int i = 0; i < itinerary.length - 1; i++) {
				double[] pos1 = degree2SVG(stringToDecimal(itinerary[i][3]), stringToDecimal(itinerary[i][4]));
				double[] pos2 = degree2SVG(stringToDecimal(itinerary[i+1][3]), stringToDecimal(itinerary[i+1][4]));
				writer.println("<line id=\"trip\" y2=\"" + pos1[1] + "\" x2=\"" + pos1[0] + "\" y1=\"" + pos2[1] + "\" x1=\"" + pos2[0] + "\" stroke-width=\"5\" stroke=\"#666666\"/>");
			}
			double[] pos1 = degree2SVG(stringToDecimal(itinerary[itinerary.length - 1][3]), stringToDecimal(itinerary[itinerary.length - 1][4]));
			double[] pos2 = degree2SVG(stringToDecimal(itinerary[0][3]), stringToDecimal(itinerary[0][4]));
			writer.println("<line id=\"trip\" y2=\"" + pos1[1] + "\" x2=\"" + pos1[0] + "\" y1=\"" + pos2[1] + "\" x1=\"" + pos2[0] + "\" stroke-width=\"5\" stroke=\"#666666\"/>");
			writer.println("</g>");
			writer.println("</svg>");
			writer.close();
		} catch (IOException e) {
			// do something
		}
	}


	public  ArrayList<Location>  buildJSON(String[][] itinerary,ArrayList<String> myKeys, ArrayList<Location>  queryResults) {

		// testing distance summation
		// setTotalDistance(itinerary);
		// System.out.println(getTotalDistance());
//      System.out.println(jsonData.get(0).get(myKeys.get(0)));
//      System.out.println(jsonData.get(0).get(myKeys.get(1)));
//      System.out.println(jsonData.get(0).get(myKeys.get(2)));
		for (int i = 0; i < queryResults.size(); i++) {
			System.out.println("start: " + itinerary[i][0]);
			System.out.println("end: " + itinerary[i][1]);
			System.out.println("distance: " + itinerary[i][2]);

			queryResults.get(i).setIt(itinerary[i][0],itinerary[i][1], (int) Math.round(Double.parseDouble(itinerary[i][2])));

		}


		//  JSONArray myArr = new JSONArray();


//      for (int i = 0; i < itinerary.length; i++) {
//
//         // Helper fucntion to match itenerary location to jsonData so that we can can get the rest of the information e.g elevation
//         // The data returned is from the ending location
//            int currentLoc = getIternaryLocation(itinerary[i][1], jsonData);
//
//            JSONObject obj = new JSONObject();
//            obj.put("start", itinerary[i][0]);
//            obj.put("end", itinerary[i][1]);
//            obj.put("distance", new Integer((int) Math.round(Double.parseDouble(itinerary[i][2]))));
//
//            // Keys are *all* the various data types loaded in from the CVS, add the data name and the values assocated to it to json
//            for (int k = 0; k < myKeys.size(); k++) {
//               obj.put(myKeys.get(k), jsonData.get(currentLoc).get(myKeys.get(k)));
//            }
//
//          //  myArr.add(obj);
//
//         }

//         try (FileWriter file = new FileWriter(this.webPath+"/test.json")) {
//
//            file.write(myArr.toJSONString());
//            file.flush();
//
//         } catch (IOException e) {
//            e.printStackTrace();
//         }



		return queryResults;
	}
}