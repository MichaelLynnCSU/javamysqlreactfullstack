package edu.csu2017fa314.T30.Model;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class TestModel 
{
    private Model m;
	private BuildItinerary i;

    @Before
    public void setUp() throws Exception 
    {
        m = new Model();

    }

    @Test
    public void testGetClosest()
    {
        ArrayList<String[]> testArray = new ArrayList<>();
        String[] test1 = {"one", "One22 Brew", "39°38'07\" N", "104°45'32\" W"};
        String[] test2 = {"two", "Two22 Brew", "39°38'08\" N", "104°45'33\" W"};
        String[] test3 = {"three", "Three22 Brew", "39°38'09\" N", "104°45'34\" W"};
        String[] test4 = {"four", "Four22 Brew", "60°38'08\" N", "10°45'33\" W"};
        testArray.addAll(Arrays.asList(test2, test3, test4));
        BuildItinerary b = new BuildItinerary(testArray,test1);
        assertArrayEquals(b.getClosest(test1, testArray), test2);
    }

    @Test
    public void testTotalDistanceZeros()
    {
        String[][] testArray = {{"one", "two", "0.0"}, {"two", "three", "0.0"}};
        assertEquals(m.totalDistance(testArray), 0.0, 0.001);
    }

    @Test
    public void testTotalDistanceZero()
    {
        String[][] testArray = {{"one", "two", "-1.0"}, {"two", "three", "1.0"}};
        assertEquals(m.totalDistance(testArray), 0.0, 0.001);
    }

    @Test
    public void testTotalDistancePositive()
    {
        String[][] testArray = {{"one", "two", "10.5"}, {"two", "three", "3.5"}};
        assertEquals(m.totalDistance(testArray), 14.0, 0.001);
    }

    @Test
    public void testTotalDistanceNegative()
    {
        String[][] testArray = {{"one", "two", "-12.1"}, {"two", "three", "-1.1"}};
        assertEquals(m.totalDistance(testArray), -13.2, 0.001);
    }

    @Test
    public void testTotalDistanceSmall()
    {
        String[][] testArray = {{"one", "two", "0.00001"}, {"two", "three", "0.000002"}};
        assertEquals(m.totalDistance(testArray), 0.000012, 0.00000001);
    }

    @Test
    public void testTotalDistanceLarge()
    {
        String[][] testArray = {{"one", "two", "100000000.0"}, {"two", "three", "20000000.0"}};
        assertEquals(m.totalDistance(testArray), 120000000.0, 0.001);
    }

    @Test
    public void shortestItineraryThree()
    {
        ArrayList<String[]> testArray = new ArrayList<>();
        String[] test1 = {"one", "One22 Brew", "39°38'07\" N", "104°45'32\" W"};
        String[] test2 = {"two", "Two22 Brew", "39°38'08\" N", "104°45'33\" W"};
        String[] test3 = {"three", "Three22 Brew", "39°38'09\" N", "104°45'34\" W"};
        testArray.addAll(Arrays.asList(test1, test2, test3));
        m.setLocations(testArray);
        String[][] itin = m.shortestItinerary();
        assertArrayEquals(itin, itin);
    }

	@Test
	public void twoOptSwapTest()
	{
	String[][] sArr = { {"a"}, {"b"}, {"c"}, {"d"}, {"e"}, {"f"} };
	String[][] sSol = { {"a"}, {"d"}, {"c"}, {"b"}, {"e"}, {"f"} };
	i.twoOptSwap(sArr, 1, 3);
	assertArrayEquals(sArr, sSol);
	}
}
