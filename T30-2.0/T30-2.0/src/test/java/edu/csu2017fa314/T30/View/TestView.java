package edu.csu2017fa314.T30.View;
import edu.csu2017fa314.T30.Model.Model;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;


public class TestView
{
    private View v;

    @Before
    public void setUp() throws Exception 
    {
        v = new View();
    }

    /*
    @Test
    public void testSetDistance()
    {

        String[][] test = new String[][]{
                {"a","b", "1"},
                {"a","b", "2"},
                {"a","b", "3"}
        };


          v.setTotalDistance(test);
          assertEquals(v.getTotalDistance(), 6);


    }
    */

    @Test
    public void testGetIternaryLocation()
    {
        ArrayList<String> myKeys = new ArrayList<String>();

        TreeMap<String, String> obj = new TreeMap<>();
        for (int i = 0; i < 3; i++) {
            obj.put("a" + i, "400");
            myKeys.add("a" + 1);
       }
       System.out.println(obj);

        ArrayList<TreeMap<String, String>> container = new ArrayList<TreeMap<String, String>>();;
        container.add(obj);

      assertEquals(v.getIternaryLocation(myKeys.get(0), container), 0);


    }



}
