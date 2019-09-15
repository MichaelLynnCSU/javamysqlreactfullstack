package edu.csu2017fa314.T14.Server;

import edu.csu2017fa314.T14.Server.ServerRequest.LoadItinerary;
import edu.csu2017fa314.T14.Util.DatabaseColumns;
import edu.csu2017fa314.T14.View.View;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.csu2017fa314.T14.Server.Database.ItineraryQueryBuilder;
import edu.csu2017fa314.T14.Server.Database.QueryBuilder;
import edu.csu2017fa314.T14.Server.Database.QueryColumns;
import edu.csu2017fa314.T14.Server.Database.FriendlyNamesQuery;
import edu.csu2017fa314.T14.Server.ServerRequest.BuildItinerary;
import edu.csu2017fa314.T14.Server.ServerRequest.QueryDatabase;
import edu.csu2017fa314.T14.Server.ServerResponse.QueryResponse;
import edu.csu2017fa314.T14.Util.ItineraryItem;
import edu.csu2017fa314.T14.Util.Location;
import spark.Request;
import spark.Response;

import static spark.Spark.path;
import static spark.Spark.port;
import static spark.Spark.post;

import edu.csu2017fa314.T14.Model.*;

public class Server {

    private Model m;

    public void serve(int port) throws InvalidLatLongException, Exception {
        DatabaseColumns.COLUMN_NAMES = this.setSQLColumns();

        port(port);
        Gson g = new Gson();

        /** Paths: ie. /api/testing
         * */
        path("/api", () -> {
            post("/testing", this::testing, g::toJson);
            post("/build", this::build);
            post("/load", this::load);
            post("/save", this::save);
        });
    }

    /*
    * TODO: Implement load server request
    */
    private Object load(Request req, Response res){
        setHeaders(res);
        JsonParser parser = new JsonParser();
        JsonElement elm = parser.parse(req.body());
        Gson gson = new Gson();
        LoadItinerary li = gson.fromJson(elm, LoadItinerary.class);
        FriendlyNamesQuery fnq = new FriendlyNamesQuery("jjmau14", "830802582"); // Create new QueryBuilder instance and pass in credentials
        ArrayList<Location> loadedLocations = new ArrayList<>();

        for (String code : li.getQuery()){
            loadedLocations.add(fnq.query("SELECT * FROM airports WHERE code = '" + code + "'"));
        }
        String json = "{\"locations\": [";
        for ( Location l : loadedLocations ){
            String temp = l.getName();
            if (temp != null){
                temp = temp.replace("\"", "'");
            }
            json += "{\"id\": \"" + l.getId() + "\", \"name\": \"" + temp + "\"},";
        }
        json = json.substring(0, json.length()-1);
        json += "]}";
        return json;
    }

    /*
    * TODO: Implement save server request
    */
    private Object save(Request req, Response res){
        return null;
    }

    private Object build(Request req, Response res){
        setHeaders(res);
        JsonParser parser = new JsonParser();
        JsonElement elm = parser.parse(req.body());
        Gson gson = new Gson();
        BuildItinerary iRec = gson.fromJson(elm, BuildItinerary.class);

        ArrayList searched = iRec.getQuery();
        ItineraryQueryBuilder q = new ItineraryQueryBuilder("jjmau14", "830802582"); // Create new QueryBuilder instance and pass in credentials

        ArrayList<ItineraryItem> itinerary = new ArrayList<>();
        for (Object code : searched){
            String queryString = String.format("select airports.home_link, airports.wikipedia_link, airports.code, airports.name, airports.latitude, airports.longitude, airports.municipality, region.name as region, country.name as country, continent.name as continent from (select home_link, wikipedia_link, name, code, latitude, longitude, continent, iso_country, iso_region, municipality from airports) as airports,(select name, code from countries) as country,(select name, code from continents) as continent, (select name, code from regions) as region where country.code = airports.iso_country and continent.code = airports.continent and region.code = airports.iso_region and airports.code = '%s' ", code);

            ItineraryItem i = q.query(queryString, DatabaseColumns.COLUMN_NAMES);
            if (i.size() > 0){
                itinerary.add(i);
            }
        }
        String json = "";
        try {
            this.m = new Model(itinerary, iRec.getMilesKilo(), iRec.getOpt());
            m.OptimizeTrip();
            View v = new View(m);
            json = v.generateJSON();
        }catch(Exception e){
            System.out.println("Error caught: " + e.getMessage());
        }
        return json;
    }



    private Object testing(Request req, Response res) {
        setHeaders(res);
        JsonParser parser = new JsonParser();
        JsonElement elm = parser.parse(req.body());
        Gson gson = new Gson();

        QueryDatabase dRec = gson.fromJson(elm, QueryDatabase.class);

        String searched = dRec.getQuery();
        /* SQL query */
        QueryBuilder q = new QueryBuilder("jjmau14", "830802582");
        String queryString = String.format("select airports.home_link, airports.wikipedia_link, airports.code, airports.name, airports.latitude, airports.longitude, airports.municipality, region.name as region, country.name as country, continent.name as continent from (select home_link, wikipedia_link, name, `code`, latitude, longitude, continent, iso_country, iso_region, municipality from airports) as airports,(select wikipedia_link, name, `code` from countries) as country,(select wikipedia_link, name, `code` from continents) as continent, (select wikipedia_link, name, `code` from regions) as region where country.code = airports.iso_country and continent.code = airports.continent and region.code = airports.iso_region and (airports.name like '%%%s%%' or airports.municipality like '%%%s%%' or region.name like '%%%s%%' or country.name like '%%%s%%' )limit 100;", searched, searched, searched, searched, searched);

        ArrayList<Location> queryResults = q.query(queryString);
        QueryResponse sRes = new QueryResponse(queryResults);

        return gson.toJson(sRes, QueryResponse.class);
    }


    private ArrayList<String> setSQLColumns(){
        QueryColumns qc = new QueryColumns("jjmau14", "830802582");
        ArrayList<String> columns = qc.query("select airports.home_link, airports.wikipedia_link, airports.code, airports.name, airports.latitude, airports.longitude, airports.municipality, region.name as region, country.name as country, continent.name as continent from (select home_link, wikipedia_link, name, code, latitude, longitude, continent, iso_country, iso_region, municipality from airports) as airports,(select name, code from countries) as country,(select name, code from continents) as continent, (select name, code from regions) as region where country.code = airports.iso_country and continent.code = airports.continent and region.code = airports.iso_region and airports.name like '%%' limit 1");
        return columns;
    }


    private void setHeaders(Response res) {
        // Declares returning type json
        res.header("Content-Type", "application/json");

        // Ok for browser to call even if different host host
        res.header("Access-Control-Allow-Origin", "*");
        res.header("Access-Control-Allow-Headers", "*");
    }
}