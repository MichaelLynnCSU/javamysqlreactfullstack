package edu.csu2017fa314.T14.Server.ServerRequest;

import java.util.ArrayList;

public class BuildItinerary {

    ArrayList query;
    int opt;
    int milesKilo;
    String startingLocationCode;

    public BuildItinerary(ArrayList query){
        this.query = query;
    }

    public ArrayList getQuery(){
        return this.query;
    }

    public void setQuery(ArrayList request){
        this.query = request;
    }

    public int getOpt(){
        return this.opt;
    }

    public void setOpt(int opt){
        this.opt = opt;
    }

    public int getMilesKilo(){
        return this.milesKilo;
    }

    public void setMilesKilo(int milesKilo){
        this.milesKilo = milesKilo;
    }

    public String toString(){
        return "{\"query\": " + this.query.toString() + ", \"opt\": " + this.opt + ", \"milesKilo\": " + this.milesKilo + "}";
    }

}
