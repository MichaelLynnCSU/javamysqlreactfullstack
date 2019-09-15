package edu.csu2017fa314.T14.Server.ServerResponse;

import java.util.ArrayList;
import edu.csu2017fa314.T14.Util.Location;

public class QueryResponse {
    private ArrayList<Location> locations;

    public QueryResponse(ArrayList locations){
        this.locations = locations;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public String toString() {
        return "ServerResponse{" +
                ", locations=" + locations +
                '}';
    }

}