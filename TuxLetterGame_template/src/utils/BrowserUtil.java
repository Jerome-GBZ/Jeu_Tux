package utils;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * This class is 
 * 
 *
 * @author gladen
 */
public class BrowserUtil {
    
    public static void launch(String fileName) {
        File htmlFile = new File(fileName);
        BrowserUtil.launch(htmlFile.toURI());
    }
        
    public static void launch(URI fileURI) {
        // By default, load an interesting web page
        String default_url = "https://fr.wikipedia.org/wiki/La_grande_question_sur_la_vie,_l%27univers_et_le_reste";
        if (fileURI.toString().equals(""))
        try {
            fileURI= new URI(default_url);
        } catch (URISyntaxException ex) {
            Logger.getLogger(BrowserUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Open a browser in Windows OS
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(fileURI);
            } catch (IOException ex) {
                Logger.getLogger(BrowserUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        // Or in Linux OSes
        } else {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + fileURI);
            } catch (IOException ex) {
                Logger.getLogger(BrowserUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
