package edu.csu2017fa314.T14.Model;

import edu.csu2017fa314.T14.Util.ItineraryItem;
import edu.csu2017fa314.T14.Util.DatabaseColumns;

import java.util.ArrayList;

public class Optimizer {

    private int[][] lookUpTable;
    private ArrayList<String> ShortestPathID;
    private ArrayList<Integer> ShortestPath;
    private Integer ShortestTotalPath;
    private ArrayList<String> IDLookup;
    private ItineraryItem[] DataList;

    public Optimizer(ItineraryItem[] DataList, int[][] lookUpTable){
        this.DataList = DataList;
        this.lookUpTable = lookUpTable;
        this.ShortestPathID = new ArrayList<>();
        this.ShortestPath = new ArrayList<>();
        this.ShortestTotalPath = Integer.MAX_VALUE;
        this.IDLookup = addIDs();
    }

    public ArrayList getShortestPath() {
        return ShortestPath;
    }

    public ArrayList getShortestPathID() {
        return ShortestPathID;
    }

    /*
     * Calculate Shortest Distance: method uses a 2D array of ints (look up
     * table) to determine shortest paths from each starting location. Method is
     * O(n^3)
     */
    public ArrayList<String> optimize(int optLevel) throws Exception {
        ArrayList<String> shortestPathID = new ArrayList<>();
        ArrayList<Integer> shortestPath = new ArrayList<>();
        ArrayList<Integer> tempShortestPath = new ArrayList<>(); // Contains
        ArrayList<String> visited = new ArrayList<>(); // Contains current

        int min;
        int currentMinIndex = 0;
        String IDofMin = "";
        int indexID = DatabaseColumns.COLUMN_NAMES.indexOf("code");
        int size = this.DataList.length;
        int cumulativeCurrentDistance = 0;

        // No Optimization
        if (optLevel == 0) {
            return noOpt(shortestPathID, shortestPath, indexID, size);

        // Nearest Neighbor + 2 Opt + 3 Opt2
        } else {
            return anyOpt(optLevel);
        }
    }

    private ArrayList<String> anyOpt(int optLevel) throws Exception {
        ArrayList<String> shortestPathID = new ArrayList<>();
        ArrayList<Integer> shortestPath = new ArrayList<>();
        ArrayList<Integer> tempShortestPath = new ArrayList<>(); // Contains
        ArrayList<String> visited = new ArrayList<>(); // Contains current

        try {
            int min;
            int currentMinIndex = 0;
            String IDofMin = "";
            int indexID = DatabaseColumns.COLUMN_NAMES.indexOf("code");
            int size = this.DataList.length;
            int cumulativeCurrentDistance;
            int nNShortestDistance = Integer.MAX_VALUE;
            for (int startIndex = 0; startIndex < size; startIndex++) {
                cumulativeCurrentDistance = 0;
                visited.clear();
                tempShortestPath.clear();
                int currentLocation = startIndex;
                visited.add(this.DataList[startIndex].get(indexID));

                for (int i = 0; i < size - 1; i++) {
                    min = Integer.MAX_VALUE;

                    for (int j = 0; j < size; j++) {
                        if (currentLocation == j)
                            continue;
                        if (lookUpTable[currentLocation][j] < min) {
                            String currentID = this.DataList[j].get(indexID);
                            if (visited.indexOf(currentID) < 0) {
                                IDofMin = currentID;
                                currentMinIndex = j;
                                min = lookUpTable[currentLocation][j];
                            }
                        }
                    }
                    tempShortestPath.add(min);
                    currentLocation = currentMinIndex;
                    cumulativeCurrentDistance += min;
                    visited.add(IDofMin);
                }
                cumulativeCurrentDistance += lookUpTable[currentLocation][0];
                tempShortestPath.add(lookUpTable[currentLocation][0]);

                visited.add(this.DataList[startIndex].get(indexID));
                String[] toBeOptimizedID = new String[visited.size()];
                if (optLevel > 1) {
                    for (int i = 0; i < visited.size(); i++) {
                        toBeOptimizedID[i] = visited.get(i);

                    }
                }

                visited.remove(visited.size() - 1);

                if (cumulativeCurrentDistance < nNShortestDistance) {
                    nNShortestDistance = cumulativeCurrentDistance;
                    shortestPath = tempShortestPath;
                    shortestPathID = visited;
                }


                if (optLevel == 2) {
                    twoOpt(toBeOptimizedID);
                }

                if (optLevel == 3) {
                    threeOpt();
                }
            }

            if (optLevel == 1) {
                this.ShortestPathID = shortestPathID;
                this.ShortestPath = shortestPath;
            }
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }

        return shortestPathID;
    }

    private ArrayList<String> noOpt(ArrayList<String> shortestPathID, ArrayList<Integer> shortestPath, int indexID, int size) {
        for (int startIndex = 0; startIndex < size - 1; startIndex++) {
            int secondIndex = startIndex + 1;
            shortestPathID.add(this.DataList[startIndex].get(indexID));
            shortestPath.add(lookUpTable[startIndex][secondIndex]);
        }

        // Add loop back distance
        shortestPathID.add(this.DataList[this.DataList.length-1].get(indexID));
        shortestPath.add(lookUpTable[lookUpTable.length-1][0]);

        this.ShortestPath = shortestPath;
        this.ShortestPathID = shortestPathID;
        return shortestPathID;
    }

    public void twoOpt(String[] toBeOptimizedID) throws Exception {
        boolean improved = true;
        ArrayList<Integer> shortestPath = new ArrayList<>();
        ArrayList<String> shortestPathID = new ArrayList<>();

        while (improved == true) {
            improved = false;
            for (int i = 0; i <= toBeOptimizedID.length - 3; i++) { // check
                // size>4
                for (int k = i + 2; k < toBeOptimizedID.length - 1; k++) {

                    int first = lookUpTable[IDLookup.indexOf(toBeOptimizedID[i])][IDLookup
                            .indexOf(toBeOptimizedID[i + 1])];
                    int second = lookUpTable[IDLookup.indexOf(toBeOptimizedID[k])][IDLookup
                            .indexOf(toBeOptimizedID[k + 1])];
                    int third = lookUpTable[IDLookup.indexOf(toBeOptimizedID[i])][IDLookup.indexOf(toBeOptimizedID[k])];
                    int fourth = lookUpTable[IDLookup.indexOf(toBeOptimizedID[i + 1])][IDLookup
                            .indexOf(toBeOptimizedID[k + 1])];
                    int changed = -first - second + third + fourth;

                    if (changed < 0) { // improvement?
                        twoOptSwap(toBeOptimizedID, i + 1, k);
                        improved = true;
                    }
                }
            }
        }

        int cumulativeCurrentDistance = 0;
        for (int k = 0; k < toBeOptimizedID.length - 1; k++) {
            int dist = lookUpTable[IDLookup.indexOf(toBeOptimizedID[k])][IDLookup
                    .indexOf(toBeOptimizedID[k + 1])];
            cumulativeCurrentDistance += dist;
        }

        if (cumulativeCurrentDistance < ShortestTotalPath) {
            ShortestTotalPath = cumulativeCurrentDistance;

            for (int l = 0; l < toBeOptimizedID.length - 1; l++) {
                shortestPathID.add(toBeOptimizedID[l]);
            }
            int i = 0;
            for (i = 0; i < shortestPathID.size() - 1; i++) {
                shortestPath.add(lookUpTable[IDLookup.indexOf(toBeOptimizedID[i])][IDLookup
                        .indexOf(toBeOptimizedID[i + 1])]);

            }
            shortestPath.add(
                    lookUpTable[IDLookup.indexOf(toBeOptimizedID[i])][IDLookup.indexOf(toBeOptimizedID[0])]);
            this.ShortestPathID = shortestPathID;
            this.ShortestPath = shortestPath;
        }

    }

    public void threeOpt() throws InvalidLatLongException {

    }

    private void twoOptSwap(String[] ID, int first, int second) { // swap in place
        while(first < second) {
            String tempID = ID[first];
            ID[first] = ID[second];
            ID[second] = tempID;

            first++; second--;

        }
    }

    public ArrayList<String> addIDs() {
        ArrayList<String> ids = new ArrayList<>();
        for (ItineraryItem item : this.DataList){
            ids.add(item.get(DatabaseColumns.COLUMN_NAMES.indexOf("code")));
        }
        return ids;
    }
}
