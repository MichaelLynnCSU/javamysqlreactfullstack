package edu.csu2017fa314.T14.Model;

import static org.junit.Assert.*;

import edu.csu2017fa314.T14.Util.ItineraryItem;
import edu.csu2017fa314.T14.Util.DatabaseColumns;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class TestModel 
{
    private Model m;
    private ArrayList<String> data;

    @Before
    public void setUp() throws Exception 
    {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("2CD2");
        temp.add("40");
        temp.add("-106");

        ArrayList<String> temp2 = new ArrayList<>();
        temp2.add("XKCD");
        temp2.add("35");
        temp2.add("-104");

        ArrayList<String> temp3 = new ArrayList<>();
        temp3.add("TEST");
        temp3.add("30");
        temp3.add("-90");

        ItineraryItem i = new ItineraryItem(temp);
        ItineraryItem i2 = new ItineraryItem(temp2);
        ItineraryItem i3 = new ItineraryItem(temp3);

        ArrayList<ItineraryItem> ia = new ArrayList<>();
        ia.add(i);
        ia.add(i2);
        ia.add(i3);

        m = new Model(ia, 0, 2);
        DatabaseColumns.COLUMN_NAMES = new ArrayList<>();
        DatabaseColumns.COLUMN_NAMES.add("code");
        DatabaseColumns.COLUMN_NAMES.add("latitude");
        DatabaseColumns.COLUMN_NAMES.add("longitude");
    }

    @Test
    public void test1() throws Exception{
        assertEquals("[2CD2, XKCD, TEST]", this.m.OptimizeTrip().toString());
    }

    @Test(expected = InvalidLatLongException.class)
    public void testException() throws InvalidLatLongException {
        m.standardize("10s.21*");
    }

    @Test
    public void AlreadyDecimalDegree() throws InvalidLatLongException {
        double val1 = m.standardize("106.2");
        assertEquals(106.2, val1, 0.0);
        double val2 = m.standardize("106.2°");
        assertEquals(106.2, val2, 0.0);
    }

    @Test
    public void DegreeMinutesToDD() throws InvalidLatLongException {
        double val = m.standardize("106°27'");
        assertEquals(106.45, val, 0.0);
    }

    @Test
    public void DegreeMinutesSecondsToDD() throws InvalidLatLongException {
        double val = m.standardize("106°27'50\"");
        assertEquals(106.4638, val, 0.0001);
    }

    @Test
    public void TestForNegativeDD() throws InvalidLatLongException {
        double val1 = m.standardize("106°27'50\" W");
        assertEquals(-106.4638, val1, 0.0001);

        double val2 = m.standardize("106°27'50\" S");
        assertEquals(-106.4638, val2, 0.0001);

        double val3 = m.standardize("106°27'50\" E");
        assertEquals(106.4638, val3, 0.0001);

        double val4 = m.standardize("106°27'50\" N");
        assertEquals(106.4638, val4, 0.0001);

    }
    
    @Test
    public void calcDist() throws InvalidLatLongException {
    	double val1 = m.standardize("40°35'35.12\" N");
    	double val2 = m.standardize("105°4'8.51\" W");
    	double val3 = m.standardize("40°34'59.13\" N");
    	double val4 = m.standardize("105°2'31.41\" W");
    	double miles = m.calculateDistance(val1, val2, val3, val4, 1);
    	double kilometers = m.calculateDistance(val1, val2, val3, val4, 0);
    	assertEquals(1, 1, 0.0001);
    	assertEquals(2, 2, 0.0001);
    }

}
