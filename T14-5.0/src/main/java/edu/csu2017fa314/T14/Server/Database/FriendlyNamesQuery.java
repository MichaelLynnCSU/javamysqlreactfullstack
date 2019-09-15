package edu.csu2017fa314.T14.Server.Database;

import java.sql.*;
import java.util.ArrayList;
import edu.csu2017fa314.T14.Util.Location;

public class FriendlyNamesQuery {

    private String user = "";
    private String pass = "";

    public FriendlyNamesQuery(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public Location query(String query) {
        Location l = new Location("", "");

        String myDriver = "com.mysql.jdbc.Driver";
        String myUrl = "jdbc:mysql://faure.cs.colostate.edu/cs314";
        try {
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, user, pass);
            try {
                Statement st = conn.createStatement();
                try {
                    ResultSet rs = st.executeQuery(query);
                    try {
                        while(rs.next()) {
                            String code = rs.getString("code");
                            String name = rs.getString("name");
                            l = new Location(code, name);
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
        return l;
    }
}