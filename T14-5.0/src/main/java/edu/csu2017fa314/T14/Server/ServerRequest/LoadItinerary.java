package edu.csu2017fa314.T14.Server.ServerRequest;

import java.util.ArrayList;

public class LoadItinerary {

    ArrayList<String> query;

    public LoadItinerary(ArrayList query){
        this.query = query;
    }

    public void setQuery(ArrayList query){
        this.query = query;
    }

    public ArrayList<String> getQuery(){
        return this.query;
    }

    public String toString(){
        return "{itinerary: " + this.query.toString() + " }";
    }

}
