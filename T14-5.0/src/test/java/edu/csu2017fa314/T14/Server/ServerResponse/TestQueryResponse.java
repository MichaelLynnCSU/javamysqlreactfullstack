package edu.csu2017fa314.T14.Server.ServerResponse;

import org.junit.Before;
import edu.csu2017fa314.T14.Util.Location;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.ArrayList;

public class TestQueryResponse {

    QueryResponse qR;

    @Before
    public void initialize(){
        Location l = new Location("abc", "def");
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(l);
        this.qR = new QueryResponse(locations);
    }

    @Test
    public void testGetters(){
        assertEquals("[Location{id='abc', name='def'}]", this.qR.getLocations().toString());
    }

}
