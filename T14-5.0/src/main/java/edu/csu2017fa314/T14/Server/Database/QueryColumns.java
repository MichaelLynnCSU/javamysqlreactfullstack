package edu.csu2017fa314.T14.Server.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSetMetaData;

public class QueryColumns {
    private String user = "";
    private String pass = "";

    public QueryColumns(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public ArrayList<String> query(String query) { // Command line args contain username and password
        ArrayList<String> columnNames = new ArrayList<>();
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
                        ResultSetMetaData rsmd = rs.getMetaData();
                        for (int i = 1 ; i <= rsmd.getColumnCount() ; i++){
                            columnNames.add(rsmd.getColumnLabel(i));
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
        return columnNames;
    }

}
