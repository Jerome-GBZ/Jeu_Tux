package utils;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gladen
 */
public class FileUtil {
    
    public static void stringToFile(String source, String fileName) throws IOException {
        FileWriter fw = null;
        File file = new File(fileName);
        // if file doesnt exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }
        fw = new FileWriter(file.getAbsoluteFile());
        try (BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(source);
            //System.out.println("Done writing to " + fileName); //For testing
        }
        fw.close();
    }    
    
}
