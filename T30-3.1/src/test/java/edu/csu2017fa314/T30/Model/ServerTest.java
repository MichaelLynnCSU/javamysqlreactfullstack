package edu.csu2017fa314.T30.Model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ServerTest {
    @Test
    public void testing() throws Exception {

            QueryBuilder q = new QueryBuilder("myou", "830652516"); // Create new QueryBuilder instance and pass in credentials //TODO update credentials
            String queryString = String.format("SELECT * FROM airports");
            ArrayList<Location> queryResults = q.query(queryString);



    }


}