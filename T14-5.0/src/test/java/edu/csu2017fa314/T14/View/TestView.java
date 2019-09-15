package edu.csu2017fa314.T14.View;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import edu.csu2017fa314.T14.Model.Model;
import java.util.ArrayList;
import edu.csu2017fa314.T14.Util.ItineraryItem;

public class TestView
{
   private View v;
   private Model m;
   private ArrayList<String> columns;

    @Before
    public void setUp() throws Exception {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("2CD2");
        temp.add("40");
        temp.add("-106");

        ArrayList<String> temp2= new ArrayList<>();
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

        this.m = new Model(ia, 0, 2);
    }

    @Test
    public void testJson() throws Exception{
        this.v = new View(this.m);
        //assertEquals("", this.v.generateJSON());
    }
    

}
