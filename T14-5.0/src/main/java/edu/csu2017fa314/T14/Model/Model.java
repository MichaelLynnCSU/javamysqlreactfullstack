package edu.csu2017fa314.T14.Model;

import edu.csu2017fa314.T14.Util.ItineraryItem;
import edu.csu2017fa314.T14.Util.DatabaseColumns;

import java.util.ArrayList;

public class Model {

	private ItineraryItem[] DataList;
	private ArrayList<String> ShortestPathID;
	private ArrayList<Integer> ShortestPath;
	int milesKilo;
	int optLevel;

	// Constructors ==========================================================
    public Model (ArrayList<ItineraryItem> DataList, int milesKilo, int optLevel) throws InvalidLatLongException{
    	this.DataList = new ItineraryItem[DataList.size()];
		for (int i = 0 ; i < DataList.size() ; i++){
			this.DataList[i] = DataList.get(i);
		}
        this.ShortestPathID = new ArrayList<>();
        this.ShortestPath = new ArrayList<>();
		this.milesKilo = milesKilo;
		this.optLevel = optLevel;
    }
	// =======================================================================

    // Getters ===============================================================
    public ItineraryItem[] getDataList() { return this.DataList; }
    public ArrayList<Integer> getShortestPath() { return this.ShortestPath; }
	public ArrayList<String> getShortestPathID() { return ShortestPathID; }
    // =======================================================================

	/*
	 * Calculate Great Circle Distance: method takes 4 doubles, the Latitude
	 * and Longitude of the start location, and the Latitude and Longitude of
	 * the ending location
	 */
	public int calculateDistance(double latStart, double longStart, double latEnd, double longEnd, int miOrKi) {
		// convert the coordinates to radians
		double _lat1 = Math.toRadians(latStart);
		double _long1 = Math.toRadians(longStart);
		double _lat2 = Math.toRadians(latEnd);
		double _long2 = Math.toRadians(longEnd);
		double long_diff = Math.abs(_long1 - _long2);
		
		//declares variables used in the calculations below
		double angle1, angle2, angle3;
		double third, fourth;
		double arctan, centralAngle, distance;
		int result;

		// radius of earth in miles
		double radius = 3958.7613;
		
		// radius of earth in kilometers
		double radiusK = 6371.00;
		
		//if miOrKi == 1 : Miles selected
		if (miOrKi == 1) {
			// perform the calculations, breaking up the formula into smaller pieces
			// - first 2 = numerator
			angle1 = Math.pow((Math.cos(_lat2) * Math.sin(long_diff)), 2);
			angle2 = Math
					.pow(Math.cos(_lat1) * Math.sin(_lat2) - Math.sin(_lat1) * Math.cos(_lat2) * Math.cos(long_diff), 2);

			// next 3 = denominator
			third = Math.sin(_lat1) * Math.sin(_lat2);
			fourth = Math.cos(_lat1) * Math.cos(_lat2) * Math.cos(long_diff);
			angle3 = (third + fourth);

			// take the arctangent of numerator and denominator
			arctan = Math.sqrt(angle1 + angle2);
			centralAngle = Math.atan2(arctan, angle3);

			// calculate the distance
			distance = centralAngle * radius;

			// round the result to an int
			result = (int) Math.round(distance);

			return result;
		}
		//otherwise Kilometers was selected
		else {
			// perform the calculations, breaking up the formula into smaller pieces
			// - first 2 = numerator
			angle1 = Math.pow((Math.cos(_lat2) * Math.sin(long_diff)), 2);
			angle2 = Math
					.pow(Math.cos(_lat1) * Math.sin(_lat2) - Math.sin(_lat1) * Math.cos(_lat2) * Math.cos(long_diff), 2);

			// next 3 = denominator
			third = Math.sin(_lat1) * Math.sin(_lat2);
			fourth = Math.cos(_lat1) * Math.cos(_lat2) * Math.cos(long_diff);
			angle3 = (third + fourth);

			// take the arctangent of numerator and denominator
			arctan = Math.sqrt(angle1 + angle2);
			centralAngle = Math.atan2(arctan, angle3);

			// calculate the distance
			distance = centralAngle * radiusK;

			// round the result to an int
			result = (int) Math.round(distance);

			return result;
		}
	}
	// =======================================================================

	/*
	 * Generate Look Up Table for Shortest Path: method returns 2D array of ints
	 * containing distnaces from every location to all other locations. Method
	 * is O(n^2)
	 */
	private int[][] generateLookUpTable(int milesKilo) throws InvalidLatLongException {

		int[][] LookUpTable = new int[this.DataList.length][this.DataList.length];
		int latIndex = DatabaseColumns.COLUMN_NAMES.indexOf("latitude");
		int longIndex = DatabaseColumns.COLUMN_NAMES.indexOf("longitude");
		boolean miles = true;

		// Generating Lookup Table
		for (int i = 0; i < this.DataList.length; i++) {
			double latStart = standardize(this.DataList[i].get(latIndex));
			double longStart = standardize(this.DataList[i].get(longIndex));
			for (int j = 0; j < this.DataList.length; j++) {
				double latEnd = standardize(this.DataList[j].get(latIndex));
				double longEnd = standardize(this.DataList[j].get(longIndex));

				//***************** NEED TO ADD NEW BOOLEAN PARAMETER **********************
				LookUpTable[i][j] = this.calculateDistance(latStart, longStart, latEnd, longEnd, milesKilo);
			}
		}
		return LookUpTable;
	}
	// =======================================================================

	/* This Function standardizes latitude and longitude to decimal degrees.
	* If the lat/long is invalid (not an accepted format), an InvalidLatLongException
	* will be thrown
	*/
    public double standardize(String s) throws InvalidLatLongException {
        double Degrees = 0.0, Minutes = 0.0, Seconds = 0.0;
        String direction = "n";

        try{
            if (s.indexOf("°") >= 0) {
                Degrees = Double.parseDouble(s.substring(0, s.indexOf("°")));
            } else {
                // If ° is not found, degree is already in decimal degree format
                try {
                    return Double.parseDouble(s);
                }catch(Exception e){
                    throw new InvalidLatLongException("Error in \"Degrees\" parsing.");
                }
            }
        }catch(NumberFormatException e){
            throw new InvalidLatLongException("Error in \"Degrees\" parsing.");
        }

        try{
            Minutes = Double.parseDouble(s.substring(s.indexOf("°")+1, s.indexOf("'")));
        }catch(NumberFormatException e){
            throw new InvalidLatLongException("Error in \"Minutes\" parsing.");
        }catch(Exception e){
            Minutes = 0.0;
        }

        try{
            Seconds = Double.parseDouble(s.substring(s.indexOf("'")+1, s.indexOf("\"")));
        }catch(NumberFormatException e){
            throw new InvalidLatLongException("Error in \"Seconds\" parsing.");
        }catch(Exception e){
            Seconds = 0.0;
        }

        try{
            direction = s.substring(s.indexOf("\"")+1, s.length()).toLowerCase().trim();
        }catch(Exception e){
            direction = "n";
        }

        double decimalDegree = Degrees + (Minutes/60) + (Seconds/3600);
        if (direction.equals("s") | direction.equals("w"))
            decimalDegree *= -1;
        return decimalDegree;
    }
    // =======================================================================


    /* Get Data by ID: method returns data associated with the given ID */
	public ItineraryItem getDataByID(String ID){
		for (ItineraryItem item : this.DataList){
			if (item.get(DatabaseColumns.COLUMN_NAMES.indexOf("code")).equals(ID)){
				return item;
			}
		}
		return null;
	}
    // =======================================================================

	public ArrayList<String> OptimizeTrip() throws Exception {
		Optimizer o = new Optimizer(this.DataList, generateLookUpTable(this.milesKilo));
		o.optimize(this.optLevel);
		this.ShortestPathID = o.getShortestPathID();
		this.ShortestPath = o.getShortestPath();
		return this.ShortestPathID;
	}
}