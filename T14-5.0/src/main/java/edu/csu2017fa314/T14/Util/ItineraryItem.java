package edu.csu2017fa314.T14.Util;

import java.util.ArrayList;

public class ItineraryItem {

    private ArrayList<String> data;

    public ItineraryItem(){
        this.data = new ArrayList<>();
    }

    public ItineraryItem(ArrayList<String> data){this.data = data;}

    public void addElem(String e){
        this.data.add(e);
    }

    public ArrayList<String> getData() {
        return data;
    }

    public String get(int index){
        return this.data.get(index);
    }

    public int size(){
        return this.data.size();
    }

    public String toString(){
        return this.data.toString();
    }
}
