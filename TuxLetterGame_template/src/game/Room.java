package game;

import utils.XMLUtil;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import java.util.logging.Level;
import java.util.logging.Logger;
// import com.sun.org.apache.xerces.internal.parsers.DOMParser;

public class Room {
    private int depth;
    private int height;
    private int width;
    private String textureBottom;
    private String textureNorth;
    private String textureEast;
    private String textureWest;
    private String textureTop;
    private String textureSouth;
    
    public Room() {
        this.depth  = 100;
        this.width  = 100;
        this.height = 100;

        this.textureBottom = "/textures/black.png";
        this.textureNorth  = "/textures/black.png";
        this.textureEast   = "/textures/black.png";
        this.textureWest   = "/textures/black.png";
    }

    public Room(String filename) {
        Document doc = fromXML(filename);

        Element dimensions = (Element) doc.getElementsByTagName("dimensions").item(0);
        this.depth  = Integer.parseInt( ((Element) dimensions.getElementsByTagName("depth").item(0)).getTextContent() );
        this.width  = Integer.parseInt( ((Element) dimensions.getElementsByTagName("width").item(0)).getTextContent() );
        this.height = Integer.parseInt( ((Element) dimensions.getElementsByTagName("height").item(0)).getTextContent() );

        Element mapping = (Element) doc.getElementsByTagName("mapping").item(0);
        this.textureBottom  = ((Element) mapping.getElementsByTagName("textureBottom").item(0)).getTextContent();
        this.textureNorth   = ((Element) mapping.getElementsByTagName("textureNorth").item(0)).getTextContent();
        this.textureEast    = ((Element) mapping.getElementsByTagName("textureEast").item(0)).getTextContent();
        this.textureWest    = ((Element) mapping.getElementsByTagName("textureWest").item(0)).getTextContent();
    }

    private Document fromXML(String filename){
        try {
            return XMLUtil.DocumentFactory.fromFile(filename);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void resetRoom(String filename) {
        Document doc = fromXML(filename);

        Element mapping = (Element) doc.getElementsByTagName("mapping").item(0);
        setTextureBottom( ((Element) mapping.getElementsByTagName("textureBottom").item(0)).getTextContent() );
        setTextureNorth(  ((Element) mapping.getElementsByTagName("textureNorth").item(0)).getTextContent()  );
        setTextureEast(   ((Element) mapping.getElementsByTagName("textureEast").item(0)).getTextContent()   );
        setTextureWest(   ((Element) mapping.getElementsByTagName("textureWest").item(0)).getTextContent()   );
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getTextureBottom() {
        return textureBottom;
    }

    public void setTextureBottom(String textureBottom) {
        this.textureBottom = textureBottom;
    }

    public String getTextureNorth() {
        return textureNorth;
    }

    public void setTextureNorth(String textureNorth) {
        this.textureNorth = textureNorth;
    }

    public String getTextureEast() {
        return textureEast;
    }

    public void setTextureEast(String textureEast) {
        this.textureEast = textureEast;
    }

    public String getTextureWest() {
        return textureWest;
    }

    public void setTextureWest(String textureWest) {
        this.textureWest = textureWest;
    }

    public String getTextureTop() {
        return textureTop;
    }

    public void setTextureTop(String textureTop) {
        this.textureTop = textureTop;
    }

    public String getTextureSouth() {
        return textureSouth;
    }

    public void setTextureSouth(String textureSouth) {
        this.textureSouth = textureSouth;
    }
}
