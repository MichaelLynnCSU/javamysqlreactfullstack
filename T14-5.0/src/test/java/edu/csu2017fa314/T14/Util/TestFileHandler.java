package edu.csu2017fa314.T14.Util;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.io.File;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import java.util.ArrayList;

public class TestFileHandler {

    private FileHandler fh;

    @Before
    public void setup(){
        fh = new FileHandler();
    }

    @Test(expected = Exception.class)
    public void loadDoesNotExist() throws Exception {
        this.fh.loadFile("doesntExist");
    }

    @Test
    public void createDirFile() throws Exception{
        this.fh.writeFile("testFile", "\"testJson\":{\"value\": \"abc\"}");
        File dir = new File("./UserSaves/");
        assertTrue(dir.exists() && dir.isDirectory());
        assertTrue(new File("./UserSaves/testFile.json").exists());
    }

    @Test
    public void readFile() throws Exception {
        this.fh.writeFile("testFile", "\"testJson\":{\"value\": \"abc\"}");
        String temp = this.fh.loadFile("testFile");
        assertEquals("\"testJson\":{\"value\": \"abc\"}", temp);
    }

    @After
    public void cleanUp(){
        File dir = new File("./UserSaves/");
        File file = new File("./UserSaves/testFile.json");
        file.delete();
        dir.delete();
    }

}
