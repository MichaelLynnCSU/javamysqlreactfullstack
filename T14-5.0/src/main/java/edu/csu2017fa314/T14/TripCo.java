package edu.csu2017fa314.T14;

import edu.csu2017fa314.T14.Server.Server;

public class TripCo {
    public static void main(String[] args) {
        int port = 4567;
        try {
            for (int i = 0 ; i < args.length ; i++){
                if (args[i].equals("-p")){
                    port = Integer.parseInt(args[i+1]);
                }
            }
            System.out.println("TripCo server starting on port " + port);
            Server s = new Server();
            s.serve(port);
        } catch (Exception e){
            ;
        }
    }
}
