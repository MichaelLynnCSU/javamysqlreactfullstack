package edu.csu2017fa314.T14.Server.ServerResponse;

import java.util.ArrayList;
import edu.csu2017fa314.T14.Util.ItineraryItem;

public class ItineraryResponse {

    ArrayList<ItineraryItem> ItineraryItems;

    public ItineraryResponse(ArrayList ItineraryItems){
        this.ItineraryItems = ItineraryItems;
    }

    public ArrayList<ItineraryItem> getItineraryItems() {
        return ItineraryItems;
    }
}
