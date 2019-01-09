package edu.csu2017fa314.T30.Model;
import edu.csu2017fa314.T30.Model.Server;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.*;

public class Model
{
	private ArrayList<String[]> locations;
	private ArrayList<TreeMap<String, String>> jsonData;
	private ArrayList<String> myKeys = new ArrayList<>();
	private String d = "";

	public Model ()
	{
		locations = new ArrayList<>();
		jsonData = new ArrayList<>();
	}

	/*
       readData takes the csv filepath from input, then reads it into this.locations
       in the format [ID, Name, City, Latitude, Longitude, ElevationFt]

       Improperly formatted lines in the file are skipped, however it does not check the
       format of the latitude and longitude columns
    */
	public void readData( ArrayList<Location> queryData) {


	//	System.out.println("testing" + queryData.size());


//		System.out.print("enter csv filepath: ");
//		Scanner sc = new Scanner(System.in);
//		String csv_filepath = sc.nextLine();


		//try (BufferedReader read = new BufferedReader(new FileReader(csv_filepath))) {


//		HashMap<String, Integer> arr_locations = new HashMap<>();

//			d = read.readLine();
//			data = d.split(",");
//
//
//			while ((d = read.readLine()) != null) {


//				String[] brewery = new String[6];
//				data = d.split(",");

//				TreeMap<String,String> obj = new TreeMap<>();

		// map keys to values for each location object
//				for (int i = 0; i < data.length; i++) {
//
//					obj.put(myKeys.get(i).trim(),data[arr_locations.get(myKeys.get(i))].trim());
//				}


		// Location Objects
//				jsonData.add(obj);
		System.out.println("size" +  queryData.size());

		for (int j = 0; j < queryData.size(); j++) {
			// number or columns
			String[] brewery = new String[9];
			for (int i = 0; i < queryData.get(0).LocationItSize(); i++) {

				//	System.out.println("testing" + queryData.get(j).locationName(i));
				brewery[i] = queryData.get(j).locationName(i);
				//	myKeys.add(data[i].trim().toLowerCase());
			}
			this.locations.add(brewery);
		}



//

//		} catch (IOException e) {

//			e.printStackTrace();
//		}

//		for (int j = 0; j < queryData.size(); j++) {
//			for (int k = 0; k < 4; k++) {
//				System.out.println(locations.get(j)[k]);
//			}
//		}



	}

	public String[][] shortestItinerary(){
		double min_distance = Double.MAX_VALUE;
		String[][] itinerary = new String[this.locations.size()][4];
		ExecutorService executor = Executors.newFixedThreadPool(this.locations.size());
		CompletionService<String[][]> threads = new ExecutorCompletionService<>(executor);

			for (String[] loc: this.locations){
			ArrayList<String[]> locations_copy = new ArrayList<>(this.locations);
			threads.submit(new BuildItinerary(locations_copy, loc));
		}


		for (int i = 0; i < this.locations.size(); i++){
			try {
				String[][] new_itinerary = threads.take().get();



				double new_distance = totalDistance(new_itinerary);
				if (new_distance < min_distance) {
					itinerary = new_itinerary;
					min_distance = new_distance;
				}
			} catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e){ }
		}
		executor.shutdown();


		return itinerary;
	}

	public double totalDistance(String[][] itinerary){
		double distance = 0;
		for (String[] location: itinerary){
			distance += Double.parseDouble(location[2]);
		}
		return distance;
	}

	public ArrayList<TreeMap<String, String>> getJsonData(){
		return this.jsonData;
	}

	public ArrayList<String> getMyKeys(){
		return this.myKeys;
	}

	//Helper for unit tests
	public void setLocations (ArrayList<String[]> locations){
		this.locations = locations;
	}
}