package edu.csu2017fa314.T14.Server.Database;

import edu.csu2017fa314.T14.Util.ItineraryItem;
import edu.csu2017fa314.T14.Util.Location;

import java.sql.*;
import java.util.ArrayList;

public class ItineraryQueryBuilder {
    private String user = "";
    private String pass = "";

    public ItineraryQueryBuilder(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public ItineraryItem query(String query, ArrayList<String> columns) { // Command line args contain username and password
        ItineraryItem itinerary = new ItineraryItem();
        String myDriver = "com.mysql.jdbc.Driver"; // Add dependencies in pom.xml
        String myUrl = "jdbc:mysql://faure.cs.colostate.edu/cs314"; // Use this line if connecting inside CSU's network
        //String myUrl = "jdbc:mysql://localhost/cs314"; // Use this line if tunneling 3306 traffic through shell
        try { // Connect to the database
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, user, pass);
            try { // Create a statement
                Statement st = conn.createStatement();
                try { // Submit a query
                    ResultSet rs = st.executeQuery(query);
                    try { // Iterate through the query results and print selected columns
                        while(rs.next()) {
                            for (String col : columns){
                                itinerary.addElem(rs.getString(col));
                            }
                        }
                    } finally {
                        rs.close();
                    }
                } finally {
                    st.close();
                }
            } finally {
                conn.close();
            }
        } catch(Exception e) { // Catches all exceptions in the nested try's
            System.err.printf("Exception: ");
            System.err.println(e.getMessage());
        }
        return itinerary;
    }
}
