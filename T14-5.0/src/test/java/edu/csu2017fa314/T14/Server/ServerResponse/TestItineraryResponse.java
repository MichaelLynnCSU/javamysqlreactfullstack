package edu.csu2017fa314.T14.Server.ServerResponse;

import edu.csu2017fa314.T14.Util.ItineraryItem;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class TestItineraryResponse {

    ItineraryResponse iR;

    @Before
    public void initialize(){
        ItineraryItem i = new ItineraryItem();
        i.addElem("Test");
        assertEquals("[Test]", i.toString());

        ArrayList<ItineraryItem> items = new ArrayList<>();
        items.add(i);

        this.iR = new ItineraryResponse(items);
    }

    @Test
    public void testGetter(){
        assertEquals("[[Test]]", iR.getItineraryItems().toString());
    }

}
