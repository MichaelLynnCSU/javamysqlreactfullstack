package edu.csu2017fa314.T14.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileHandler {

    /**
     * @author: Josh Mau - 10/31/17
     * Writes file containing save information: User saves will be stored in the
     * `UserSaves/` directory as a .json file. If the directory does not exist,
     * it will be automatically generated. Throws exception on catch, otherwise successful.
     * */
    public void writeFile(String fileNameIdentifier, String json) throws Exception {
        File storeDirectory = new File("./UserSaves");
        if (!storeDirectory.exists()){
            storeDirectory.mkdir();
        }

        try {
            PrintWriter pw = new PrintWriter("./UserSaves/" + fileNameIdentifier + ".json", "UTF-8");
            pw.write(json);
            pw.close();
        }catch(Exception e){
            throw new Exception("Error creating or writing file: " + e.getMessage());
        }
    }
    // ======================================================================================


    /**
     * @author: Josh Mau - 10/31/17
     * Loads file by name containing json object of a saved user itinerary. If the file
     * does not exist, throws a FileNotFound Exception. On an unknown error, throws
     * IOException. Returns json as a string.
     * */
    public String loadFile(String fileNameIdentifier) throws Exception{
        String json = "";

        try {
            Scanner fileReader = new Scanner(new File("./UserSaves/" + fileNameIdentifier + ".json"));
            while (fileReader.hasNextLine()) {
                json += fileReader.nextLine();
            }
            fileReader.close();
        }catch(FileNotFoundException fnfe){
            throw new FileNotFoundException("User save file requested does not exist: " + fnfe.getMessage());
        }catch(Exception e){
            throw new Exception("Unexpected error: " + e.getMessage());
        }
        return json;
    }
    // ======================================================================================

}
