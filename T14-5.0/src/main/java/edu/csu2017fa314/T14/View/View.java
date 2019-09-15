package edu.csu2017fa314.T14.View;

import edu.csu2017fa314.T14.Model.InvalidLatLongException;
import edu.csu2017fa314.T14.Model.Model;
import edu.csu2017fa314.T14.Util.DatabaseColumns;
import edu.csu2017fa314.T14.Util.ItineraryItem;
import java.util.ArrayList;

public class View {

    private Model m;
    private ArrayList<String> ShortestPathID;
    private ArrayList<Integer> ShortestPath;

    public View(Model m) throws InvalidLatLongException{
        this.m = m;
        ShortestPathID = m.getShortestPathID();
        ShortestPath = m.getShortestPath();
    }


    public String generateJSON() throws Exception {

        String json = "{\"columns\": [";
        for (int i = 0; i < DatabaseColumns.COLUMN_NAMES.size() ; i++){
         	json += "{\"name\":\"" + DatabaseColumns.COLUMN_NAMES.get(i) + "\"},";
        }
        json = json.substring(0, json.length()-1);
        json += "],\"itinerary\": [";

        for (int i = 0; i < ShortestPathID.size() - 1; i++) {

            try {

                String id1 = this.ShortestPathID.get(i);
                String id2 = this.ShortestPathID.get(i+1);

                json += "{" +
                        "\"start\": \"" + id1 + "\"," +
                        "\"end\": \"" + id2 + "\"," +
                        "\"distance\": " + this.ShortestPath.get(i) + "," +
                        "\"startData\": {" + addData(id1) + "," +
                        "\"endData\": {" + addData(id2) +
                        "},";

            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }

        // Append final loop-back location to make a circle
        try {
            json += "{" +
                    "\"start\": \"" + this.ShortestPathID.get(this.ShortestPathID.size()-1) + "\"," +
                    "\"end\": \"" + this.ShortestPathID.get(0) + "\"," +
                    "\"distance\": " + this.ShortestPath.get(this.ShortestPath.size()-1) + "," +
                    "\"startData\": {" + addData(ShortestPathID.get(this.ShortestPathID.size()-1)) + "," +
                    "\"endData\": {" + addData(ShortestPathID.get(0)) +
                    "}]}";

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        //System.out.println(json);
        return json;
    }

    private String addData(String id){
        ItineraryItem d = m.getDataByID(id);
        String s = "";
        int counter = 0;
        for (String column : DatabaseColumns.COLUMN_NAMES){
            String temp = d.get(counter);
            if (temp != null){
                temp = temp.replace("\"", "'");
            }
            s += "\"" + column + "\": \"" + temp + "\",";
            counter++;
        }
        return s.substring(0, s.length()-1) + "}";
    }

}
