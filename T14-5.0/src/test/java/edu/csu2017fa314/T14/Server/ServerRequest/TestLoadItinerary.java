package edu.csu2017fa314.T14.Server.ServerRequest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class TestLoadItinerary {

    private LoadItinerary l;

    @Before
    public void initialize(){
        this.l = new LoadItinerary(new ArrayList());
    }

    @Test
    public void TestGettersAndSetters(){
        ArrayList<String> temp = new ArrayList<>();
        temp.add("ABCD");
        temp.add("DEFG");
        this.l.setQuery(temp);
        assertEquals(this.l.getQuery().toString(), "[ABCD, DEFG]");
    }

}
