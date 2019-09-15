package edu.csu2017fa314.T14.Server.ServerRequest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class TestQueryDatabase {

    QueryDatabase q;

    @Before
    public void initialize(){
        this.q = new QueryDatabase("", "");
    }

    @Test
    public void TestGettersAndSetters(){
        this.q.setId("1");
        this.q.setQuery("TEST");
        assertEquals("1", this.q.getId());
        assertEquals("TEST", this.q.getQuery());
    }

}
