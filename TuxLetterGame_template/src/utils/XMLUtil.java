package utils;

// see public class comment below
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * A utility class that facilitates the manipulation of DOM document, including
 * the evaluation of XPath.
 * <p>
 * Beware: xmlns (default namespace) can not be specified.
 * <p>
 * See inner classes for more information.
 * <p>
 * To simplify usage all methods are static and are declared to throw an
 * indistinct Exception.
 * <p>
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see {@link http://www.gnu.org/licenses/}.
 *
 * @author (C) 2010-2018 Emmanuel Promayon, Universite Joseph Fourier -
 * TIMC-IMAG
 * @coauthor (C) 2017-2018 Nicolas Glade, Universite Grenoble Alpes - TIMC-IMAG
 *
 */
public class XMLUtil {

    /**
     * Inner class to evaluate XPath expression on a DOM Document
     */
    static public class XPathEvaluateExpression {

        /**
         * To use when the XPath evaluation is expected to return a text
         * content.
         *
         * @param xpathExpression the xpath expression to evaluate
         * @param doc the DOM Document to use for the expression
         * @return the resulting evaluation as a String
         * @throws java.lang.Exception
         *
         */
        public static String getString(String xpathExpression, Document doc) throws Exception {
            // Factory Instanciation
            XPathFactory factory = XPathFactory.newInstance();
            // Create a new XPath object
            XPath xpath = factory.newXPath();
            // Compilation of the expression (String -> XPath)
            XPathExpression expression = xpath.compile(xpathExpression);
            // Evaluation of the XPath expression on doc (only first text answer is collected and returned)
            return (String) expression.evaluate(doc, XPathConstants.STRING);
        }

        /**
         * To use when the XPath evaluation is expected to return a number.
         *
         * @param xpathExpression the xpath expression to evaluate
         * @param doc the DOM Document to use for the expression
         * @return the resulting evaluation as a number (double)
         * @throws java.lang.Exception
         */
        public static double getNumber(String xpathExpression, Document doc) throws Exception {
            // Factory Instanciation
            XPathFactory factory = XPathFactory.newInstance();
            // Create a new XPath object
            XPath xpath = factory.newXPath();
            // Compilation of the expression (String -> XPath)
            XPathExpression expression = xpath.compile(xpathExpression);
            // Evaluation of the XPath expression on doc (only first text answer is collected and returned)
            return (Double) expression.evaluate(doc, XPathConstants.NUMBER);
        }

        /**
         * To use when the XPath evaluation is expected to return a list of
         * nodes
         *
         * @param xpathExpression the xpath expression to evaluate
         * @param doc the DOM Document to use for the expression
         * @return the resulting evaluation as a DOM list of nodes
         * @throws java.lang.Exception
         */
        public static NodeList getNodeList(String xpathExpression, Document doc) throws Exception {
            // Factory Instanciation
            XPathFactory factory = XPathFactory.newInstance();
            // Create a new XPath object
            XPath xpath = factory.newXPath();
            // Compilation of the expression (String -> XPath)
            XPathExpression expression = xpath.compile(xpathExpression);
            // Evaluation of the XPath expression on doc
            return (NodeList) expression.evaluate(doc, XPathConstants.NODESET);
        }
    } // ends XPathEvaluateExpression

    /**
     * Inner class to create DOM document with various methods including URL,
     * XSL transformation and files.
     */
    static public class DocumentFactory {

        /**
         * build a DOM Document from a String: this methods parse a String that
         * should represent an XML serialisation.
         *
         * @param xmlString the XML document as a String
         * @return the DOM Document build from the XML document.
         * @throws java.lang.Exception
         */
        static public Document fromString(String xmlString) throws Exception {
            // initialize DOM context
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true); // never forget this!

            // build the DOM document from the String
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        }

        /**
         * build a DOM Document using a URL: this methods download the URL in a
         * String.
         * <p>
         * Note: the URL should refer to an XML document.
         *
         * @param url the XML document to download
         * @return the DOM Document build from the XML document.
         * @throws java.lang.Exception
         */
        static public Document fromURL(URL url) throws Exception {
                // download the document and transform it to a flat String
                String xmlDocument = URLUtil.newString(url);

                // initialize DOM context
                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                domFactory.setNamespaceAware(true); // never forget this!

                // build the DOM document from the String
                DocumentBuilder builder = domFactory.newDocumentBuilder();
                Document doc = builder.parse(new InputSource(new StringReader(xmlDocument)));
                return doc;
        }

        /**
         * Cette méthode converti une instance d'objet java.lang.Object en
         * document DOM en réalisant un marshalling partir de cette instance.
         *
         * @param obj : an object, supposed to be an instance of a serializable
         * object (JAXB Binding)
         * @return a DOM document
         * @throws javax.xml.bind.JAXBException
         * @throws javax.xml.parsers.ParserConfigurationException
         * @throws org.xml.sax.SAXException
         * @throws java.io.IOException
         */
        static public Document fromObject(java.lang.Object obj) throws JAXBException, ParserConfigurationException, SAXException, IOException {
                // creates a string writer (contains a string buffer)
                StringWriter writer = new StringWriter();
                // Creates a JAXB Context from this instance of java.lang.Object
                JAXBContext jaxbCtx = JAXBContext.newInstance(obj.getClass());
                // Creates a marshaller
                javax.xml.bind.Marshaller marshaller;
                marshaller = jaxbCtx.createMarshaller();
                // quelques paramètres pour le support du bon encoding et pour l'affichage simplifié pour les humains
                marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
                marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                // do the marshalling towards a writer (stores the result into the string buffer)
                marshaller.marshal(obj, writer);
                // parse the StringWriter to get a DOM Document
                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = domFactory.newDocumentBuilder();
                Document output = builder.parse(new InputSource(new StringReader(writer.toString())));
                return output;
        }

        /**
         * Cette méthode applique une transformation à un Document DOM et
         * renvoie le document transformé sous la forme d'une chaine de
         * caractères (String).
         *
         * @param doc the DOM document to process by XSLT
         * @param t a transformer to be used to process the Document doc
         * @return a Document resulting from the XSL transformation of doc
         * @throws javax.xml.transform.TransformerException
         */
        public static Document fromTransformation(Transformer t, Document doc) throws TransformerException {
            // règle l'encodage du transformateur
            t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            // Crée un résultat de type StreamResult (flux) pour stocker le résulat de la transformation
            StreamResult result = new StreamResult(new StringWriter());
            // Convertit le document DOM en source exploitable par le transformateur
            DOMSource source = new DOMSource(doc);
            // Transforme le document et stocke le résultat dans result
            t.transform(source, result);
            // convertit le résultat (StreamResult) en Document renvoyé par la méthode
            return (Document) result;
        }

        /**
         * return the DOM document resulting from of an XSL transformation of a
         * source DOM document
         *
         * @param xslStreamSource a source containing the XSL stylesheet
         * @param doc the DOM document to process by XSLT
         * @return the DOM document resulting from the XSL transformation of doc
         * as written in the stylesheet xslFileName
         * @throws java.lang.Exception
         */
        public static Document fromXSLTransformation(StreamSource xslStreamSource, Document doc) throws Exception {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            //domFactory.setNamespaceAware(true); // never forget this!
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document output = builder.newDocument();
            Result result = new DOMResult(output);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer t = transformerFactory.newTransformer(xslStreamSource);
            t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            t.transform(new DOMSource(doc), result);
            return (Document) result;
        }

        /**
         * return the DOM document resulting from of an XSL transformation of a
         * source DOM document
         *
         * @param xslFileName name of the file containing the XSL stylesheet
         * @param doc the DOM document to process by XSLT
         * @return the DOM document resulting from the XSL transformation of doc
         * as written in the stylesheet xslFileName
         * @throws java.lang.Exception
         */
        public static Document fromXSLTransformation(String xslFileName, Document doc) throws Exception {
            return fromXSLTransformation(new StreamSource(xslFileName), doc);
        }

        /**
         * build a DOM Document from an XML file.
         *
         * @param fileName name of the input file (have to be XML)
         * @return the corresponding DOM Document
         * @throws java.lang.Exception
         */
        public static Document fromFile(String fileName) throws Exception {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true); // never forget this! // XXXXXX
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document theDocument = builder.parse(new File(fileName));
            return theDocument;
        }

        /**
         * build a DOM Document from a Streamsource.
         *
         * @param source a stream source providing a XML content
         * @return the corresponding DOM Document
         * @throws java.lang.Exception
         */
        public static Document fromStreamSource(StreamSource source) throws Exception {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true); // never forget this!  // XXXXXX
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setSystemId(source.getSystemId());
            is.setByteStream(source.getInputStream());
            is.setCharacterStream(source.getReader());
            Document theDocument = builder.parse(is);
            return theDocument;
        }
    } // ends DocumentFactory

    /**
     * Inner class to apply XSL transformations to serialize DOM document into
     * String or files
     */
    static public class DocumentTransform {

        /**
         * write a DOM Document in a XML file
         *
         * @param doc the DOM Document to serialize in a file
         * @param outputFileName the file name to write to
         * @throws java.lang.Exception
         */
        public static void writeDoc(Document doc, String outputFileName) throws Exception {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            DocumentType dt = doc.getDoctype();
            if (dt != null) {
                String pub = dt.getPublicId();
                if (pub != null) {
                    t.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, pub);
                }
                t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, dt.getSystemId());
            }
            t.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); // NOI18N

            t.setOutputProperty(OutputKeys.INDENT, "yes"); // NOI18N

            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // NOI18N

            Source source = new DOMSource(doc);
            Result result = new StreamResult(new FileOutputStream(outputFileName));
            t.transform(source, result);

        }

        /**
         * Cette méthode applique une transformation à un Document DOM et
         * renvoie le document transformé sous la forme d'une chaine de
         * caractères (String).
         *
         * @param doc the DOM document to process by XSLT
         * @param t a transformer to be used to process the Document doc
         * @return a String resulting from the XSL transformation of doc
         * @throws javax.xml.transform.TransformerException
         */
        public static String fromTransformation(Transformer t, Document doc) throws TransformerException {
            // règle l'encodage du transformateur
            t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            // Crée un résultat de type StreamResult (flux) pour stocker le résulat de la transformation
            StreamResult result = new StreamResult(new StringWriter());
            // Convertit le document DOM en source exploitable par le transformateur
            DOMSource source = new DOMSource(doc);

            // Transforme le document et stocke le résultat dans result
            t.transform(source, result);

            // convertit le résultat (StreamResult) en String renvoyé par la méthode
            String xmlString = result.getWriter().toString();
            return xmlString;
        }

        /**
         * Cette méthode transforme un document DOM en XML.
         *
         * @param doc the DOM document to process by XSLT
         * @return a String resulting from the default transformation of doc, as
         * to say it is serialized into a XML String
         * @throws javax.xml.transform.TransformerConfigurationException
         */
        public static String fromDefaultTransformation(Document doc) throws TransformerConfigurationException, TransformerException {
            // Crée un transformateur de Documents DOM à partir d'une fabrique de transformateurs
            // le transformateur est créé par défaut : il transformera en XML
            Transformer t = TransformerFactory.newInstance().newTransformer();
            // renvoie la chaine de caractere après transformation
            return fromTransformation(t, doc);
        }

        /**
         * Cette méthode transforme un document DOM par l'intermédiaire d'une
         * feuille de transformation xsl dont le flux source (SourceStream) est
         * fourni en paramètre. Elle renvoie le document transformé sous la
         * forme d'une chaine de caractères (String).
         *
         * @param xslStreamSource a stream source providing the XSL stylesheet
         * @param doc the DOM document to process by XSLT
         * @return a String resulting from the XSL transformation of doc as
         * written in the stylesheet provided by the stream source
         * @throws javax.xml.transform.TransformerConfigurationException
         */
        public static String fromXSLTransformation(StreamSource xslStreamSource, Document doc) throws TransformerConfigurationException, TransformerException {
            // Crée un transformateur de Documents DOM à partir d'une fabrique de transformateurs
            // le transformateur est créé à partir d'une source provenant de la feuille de transformation XSL, 
            // donc il transformera le Document selon cette feuille XSL
            Transformer t = TransformerFactory.newInstance().newTransformer(xslStreamSource);
            // renvoie la chaine de caractere après transformation
            return fromTransformation(t, doc);
        }

        /**
         * return the String resulting from of an XSL transformation of a source
         * DOM document
         *
         * @param xslFileName name of the file containing the XSL stylesheet
         * @param doc the DOM document to process by XSLT
         * @return a String resulting from the XSL transformation of doc as
         * written in the stylesheet xslFileName
         * @throws java.lang.Exception
         */
        public static String fromXSLTransformation(String xslFileName, Document doc) throws Exception {
            return fromXSLTransformation(new StreamSource(xslFileName), doc);
        }
    }
}
