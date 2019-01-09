package edu.csu2017fa314.T30.Model;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.Arrays;

public class BuildItinerary implements Callable<String[][]>{

        private ArrayList<String[]> locations;
        private String[] start;

    public BuildItinerary (ArrayList<String[]> locations, String[] start) {
        this.locations = locations;
        this.start = start;
    }

    public String[][] call(){
        int size = locations.size();
        String[][] itinerary = new String[size][6];
        String[] current = start;
        locations.remove(current);
        String[] closest;
        int loc_size = locations.size();
        for (int i = 0; i < loc_size; i++) {
            closest = this.getClosest(current, locations);
            itinerary[i][0] = current[1];
            itinerary[i][1] = closest[1];
            itinerary[i][2] = Double.toString(computeGCD(current[2], current[3], closest[2], closest[3]));
            itinerary[i][3] = current[2];
            itinerary[i][4] = current[3];
            current = closest;
            locations.remove(current);
        }
        itinerary[size-1][0] = current[1];
        itinerary[size-1][1] = start[1];
        itinerary[size-1][2] = Double.toString(computeGCD(current[2], current[3], start[2], start[3]));
        itinerary[size-1][3] = current[2];
        itinerary[size-1][4] = current[3];


		//Starting 2opt
		for(int i=0; i<size; i++)
			itinerary[i][5] = Integer.toString(i);
		double[][] distanceArray = buildDistanceArray(itinerary);
		boolean improved = true;
		while (improved){
		improved = false;
		for (int i=0; i<size-3; i++)
			for(int k=i+1; k<size-1; k++){
			double newDist = distanceArray[Integer.parseInt(itinerary[i][5])][Integer.parseInt(itinerary[k][5])] + distanceArray[Integer.parseInt(itinerary[i+1][5])][Integer.parseInt(itinerary[k+1][5])] ;
			double oldDist = distanceArray[Integer.parseInt(itinerary[i][5])][Integer.parseInt(itinerary[i+1][5])] + distanceArray[Integer.parseInt(itinerary[k][5])][Integer.parseInt(itinerary[k+1][5])] ;
			if (newDist < oldDist) {
				improved = true;
				twoOptSwap(itinerary, i+1, k);
				
				}
			}
		}
		for (int i=0; i<size-1; i++){
			itinerary[i][1] = itinerary[i+1][0];
			itinerary[i][2] = Double.toString(distanceArray[Integer.parseInt(itinerary[i][5])][Integer.parseInt(itinerary[i+1][5])]);
		}
		itinerary[size-1][1] = itinerary[0][0];
		itinerary[size-1][2] = Double.toString(distanceArray[Integer.parseInt(itinerary[size-1][5])][Integer.parseInt(itinerary[0][5])]);
		//2opt finished
		return itinerary;
    }

	//propogates change in route for swap
	public static void twoOptSwap(String[][] itin, int i, int k){
		while (i<k){
			String[] temp = itin[i];
			itin[i] = itin[k];
			itin[k] = temp;
			i++; k--;
		}
	}

	public double[][] buildDistanceArray(String[][] itinerary){
		double[][] distanceArray = new double[itinerary.length][itinerary.length];
		for (int i = 0; i < itinerary.length; i++)
			for (int j=0; j<itinerary.length; j++) {
				if (i != j) {
					double x1 = stringToDecimal(itinerary[i][3]); double y1 = stringToDecimal(itinerary[i][4]); 
					double x2 = stringToDecimal(itinerary[j][3]); double y2 = stringToDecimal(itinerary[j][4]);
					distanceArray[i][j] = computeGCD(x1, y1, x2, y2);
				}
			}
		return distanceArray;
	}

    public String[] getClosest(String[] current, ArrayList<String[]> locations){
        String[] closest = locations.get(0);
        double current_GCD = computeGCD(current[2],current[3], closest[2], closest[3]);
        for (int i = 1; i < locations.size(); i++){
            double new_GCD = computeGCD(current[2],current[3], locations.get(i)[2], locations.get(i)[3]);
            if (new_GCD < current_GCD && new_GCD != 0){
                closest = locations.get(i);
                current_GCD = new_GCD;
            }
        }
        return closest;
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
}
