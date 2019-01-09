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

    	@Test
	public void testdegree2SVG1() {
		double[] svgVals = {38, 38};
		double[] methodVals = v.degree2SVG(41.0, -109.0);
		assertEquals(methodVals[0], svgVals[0], 0.001); 
		assertEquals(methodVals[1], svgVals[1], 0.001); 
	}
	
	@Test
	public void testdegree2SVG2() {
		double[] svgVals = {38, 745};
		double[] methodVals = v.degree2SVG(37.0, -109.0);
		assertEquals(methodVals[0], svgVals[0], 0.001); 
		assertEquals(methodVals[1], svgVals[1], 0.001); 
	}
	
	@Test
	public void testdegree2SVG3() {
		double[] svgVals = {1028, 38};
		double[] methodVals = v.degree2SVG(41.0, -102.0);
		assertEquals(methodVals[0], svgVals[0], 0.001); 
		assertEquals(methodVals[1], svgVals[1], 0.001); 
	}
	
	@Test
	public void testdegree2SVG4() {
		double[] svgVals = {1028, 745};
		double[] methodVals = v.degree2SVG(37.0, -102.0);
		assertEquals(methodVals[0], svgVals[0], 0.001); 
		assertEquals(methodVals[1], svgVals[1], 0.001); 
	}

	@Test
	public void teststringToDecimal1() {
		double decimalVal = 73.65;
		assertEquals(v.stringToDecimal("73°39'"), decimalVal, .01);
	}

	@Test
	public void teststringToDecimal2() {
		double decimalVal = 139.0;
		assertEquals(v.stringToDecimal("139°"), decimalVal, .01);
	}

	@Test
	public void teststringToDecimal3() {
		double decimalVal = 47.88;
		assertEquals(v.stringToDecimal("47°53'"), decimalVal, .01);
	}

	@Test
	public void teststringToDecimal4() {
		double decimalVal = 39.4;
		assertEquals(v.stringToDecimal("39°24'05"), decimalVal, .01);
	}


}
