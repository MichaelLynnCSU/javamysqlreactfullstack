package edu.csu2017fa314.T30.Model;

import java.util.ArrayList;

public class Location {
    private String id;
    private String name;
    private String type;
    private String latitude;
    private String longitude;
    private String elevation;
    private String municipality;
    private String home_link;
    private String wikipedia_link;
    String start;
    String end;
    int  distance;

    ArrayList<String> col = new ArrayList<>();




    public Location(String id, String name, String type,String latitude, String longitude,String elevation,String municipality,String home_link,String wikipedia_link) {        this.id = id;
        this.name = name;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.municipality = municipality;
        this.home_link = home_link;
        this.wikipedia_link = wikipedia_link;
        this.start = start;
        this.end = end;
        this.distance = distance;

    }

    public void setIt(String start,String end, int distance){

        this.start = start;
        this.end = end;
        this.distance = distance;
    }


    public int dataBaseSize(){
        return 9;
    }

    public int LocationItSize(){
        return 4;
    }

      public String locationName(int index)
      {
          if(index == 0) {
              return id;
          }
          if(index == 1) {
              return name;
          }
          if(index == 2){
              return latitude;
          }
          if(index == 3){
              return longitude;
          }
         return "locations";
      }

    public String GetJsonData(int index)
    {
        if(index == 0) {
            return id;
        }
        if(index == 1) {
            return name;
        }
        if(index == 2){
            return type;
        }
        if(index == 3){
            return latitude;
        }
        if(index == 4){
            return longitude;
        }
        if(index == 5){
            return elevation;
        }
        if(index == 6){
            return municipality;
        }
        if(index == 7){
            return home_link;
        }
        if(index == 8){
            return wikipedia_link;
        }

        return "Json";
    }

    public  ArrayList<String> GetColumnsNames()
    {

        col.add("id");
        col.add("name");
        col.add("type");
        col.add("latitude");
        col.add("longitude");
        col.add("elevation");
        col.add("municipality");
        col.add("home_link");
        col.add("wikipedia_link");

        return col;
    }



    @Override
    public String toString() {
        return "Location{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", name='" + type + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", elevation='" + elevation + '\'' +
                ", municipality='" + municipality + '\'' +
                ", home_link='" + home_link + '\'' +
                ", wikipedia_link='" + wikipedia_link + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", distance='" + distance + '\'' +

                '}';
    }
}
