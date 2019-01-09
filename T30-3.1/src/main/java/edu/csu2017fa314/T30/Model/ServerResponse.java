package edu.csu2017fa314.T30.Model;
import java.util.ArrayList;

public class ServerResponse {
    private String svg = "";
    private ArrayList<Location> locations;

    public ServerResponse(String svg, ArrayList locations) {
        this.svg = svg;
        this.locations = locations;
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "svg='" + svg + '\'' +
                ", locations=" + locations +
                '}';
    }
}