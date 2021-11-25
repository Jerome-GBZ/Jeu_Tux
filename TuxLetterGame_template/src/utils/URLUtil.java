package utils;

// see public class comment below
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Utility class to do different things from a URL and
 * manipulating the JVM proxy (setting/unsetting).
 * <p> 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see {@link http://www.gnu.org/licenses/}.
 *
 * @author (C) 2010-2018 Emmanuel Promayon, Universite Joseph Fourier - TIMC-IMAG
 *
 */
public class URLUtil {

    /** Download a html document from a URL and save it to a file
     * <p>
     * Warning: writing an html file can be problematic as this class does not
     * use any thread.
     * 
     * @param url the html document to download
     * @param outputFileName the file name to save the URL to
     * @throws java.lang.Exception
     */
    static public void writeHtml(URL url, String outputFileName) throws Exception {
        URLConnection uc = url.openConnection();
        uc.setDoInput(true);
        uc.setUseCaches(false);

        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream())); 
        FileWriter fstream = new FileWriter(outputFileName);
        BufferedWriter out = new BufferedWriter(fstream);
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            out.write(inputLine);
        }
        
        in.close();
        out.close();
    }

    /** Download a binary file from a URL (an image for example) and save it to a file
     * 
     * @param url the binary document to download
     * @param outputFileName the file name to save the URL to
     * @throws java.lang.Exception
     */
    static public void writeBinaryFile(URL url, String outputFileName) throws Exception {
        // http connection
        URLConnection uc = url.openConnection();
        // Get the data as a buffer of byte
        InputStream raw = uc.getInputStream();
        InputStream in = new BufferedInputStream(raw);
        byte[] data = new byte[uc.getContentLength()];
        int bytesRead = 0;
        int offset = 0;
        while (offset < uc.getContentLength()) {
            bytesRead = in.read(data, offset, data.length - offset);
            if (bytesRead == -1) {
                break;
            }
            offset += bytesRead;
        }
        in.close();

        if (offset != uc.getContentLength()) {
            throw new IOException("Only read " + offset + " bytes; Expected " + uc.getContentLength() + " bytes");
        }

        // sauvegarde dans un fichier
        FileOutputStream out = new FileOutputStream(outputFileName);
        out.write(data);
        out.flush();
        out.close();
    }

    /** Download a document from a URL and save it as a flat (probably rather long) String
     * 
     * @param url the URL of the source
     * @return the document (as serialized characters)
     * @throws java.lang.Exception
     */ 
    static public String newString(URL url) throws Exception {
        // connection http

        URLConnection uc = url.openConnection();
        // récupération de la réponse dans un buffer de caractères
        String inputLine;
        StringBuffer buffer = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(),"UTF-8"));
        while ((inputLine = in.readLine()) != null) {
            buffer = buffer.append(inputLine);
        }
        in.close();
        return buffer.toString();
    }
    
    /** Set the proxy for the JVM to work from the UJF network
     * 
     */
    static public void setUJFProxy() {
        setProxy("www-cache.ujf-grenoble.fr","3128");
    }
    
    /** Set the proxy to any host, any port
     * 
     * @param host the proxy host
     * @param port the port on the host
     */
    static public void setProxy(String host, String port) {
        System.getProperties().put("proxySet", "true");
        System.getProperties().put("http.proxyHost", host);
        System.getProperties().put("http.proxyPort", port);     
    }
    
    /** Unset the proxy: once invoked, no proxy will be used,
     *  just a direct connection to the internet.
     */
    static public void unsetProxy() {
        System.getProperties().put("proxySet", "false");
    }

}
