package game;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class DicoHandler extends DefaultHandler{

    //SAX VAR
    private StringBuffer buffer;
    private int mNiveau = 0;
    private Dico dico;
    
    public DicoHandler(Dico dico){ 
        super();
        this.dico = dico;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        buffer = new StringBuffer(); 
        if(qName.equals("dictionnaire")) {

        } else if(qName.equals("mot")) {
            try { 
				mNiveau = Integer.parseInt(attributes.getValue("niveau")); 
			} catch(Exception e){ 
				//erreur, le contenu de niveau n'est pas un entier 
				throw new SAXException(e); 
			} 
        } else{
            //erreur, on peut lever une exception 
		    throw new SAXException("Balise "+qName+" inconnue."); 
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.out.println("end element");

        if(qName.equals("dico")){ 
		}else if(qName.equals("mot")){
            
            String mot = buffer.toString(); 
            System.out.println("Ajout du mot : "+mot+" de niveau: "+ mNiveau);
            dico.ajouteMotADico(mNiveau, mot);
			buffer = null;
            mNiveau = 0;
        }
    }
        
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String lecture = new String(ch,start,length); 
		if(buffer != null) buffer.append(lecture);
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Début du parsing"); 
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("Fin du parsing"); 
		System.out.println("Resultats du parsing"); 
		
    } 
}