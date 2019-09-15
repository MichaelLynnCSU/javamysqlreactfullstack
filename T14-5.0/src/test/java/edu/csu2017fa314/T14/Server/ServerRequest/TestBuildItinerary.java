package edu.csu2017fa314.T14.Server.ServerRequest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class TestBuildItinerary {

    BuildItinerary b;

    @Before
    public void initialize(){
        this.b = new BuildItinerary(new ArrayList<>());
    }

    @Test
    public void testSettersAndGetters(){
        this.b.setMilesKilo(1);
        this.b.setOpt(2);
        ArrayList<String> x = new ArrayList<>();
        x.add("ABCD");
        x.add("DEFG");
        this.b.setQuery(x);
        assertTrue(this.b.getMilesKilo() == 1);
        assertTrue(this.b.getOpt() == 2);
        assertEquals(this.b.getQuery().toString(), "[ABCD, DEFG]");
    }

}
