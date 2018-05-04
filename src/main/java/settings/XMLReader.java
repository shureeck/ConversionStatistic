package settings;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static constants.StringsConstant.*;

public class XMLReader {

    private Node root;
    private Node databse;
    private NodeList tabs;

   public XMLReader(String path){
       root = readXML(path);
       tabs = ((Element)root).getElementsByTagName(TAB);
       Node databse=((Element)root).getElementsByTagName(CONNECT).item(0);
   }


    private Node readXML(String path)
    {
        Document doc=null;
        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
            doc=docBuilder.parse(path);
            doc.getDocumentElement().normalize();

        }//try

        catch(javax.xml.parsers.ParserConfigurationException e){
            //Logger.setLog(e.getStackTrace().toString());
            e.printStackTrace();
        }//catch
        catch(org.xml.sax.SAXException e){
            // Logger.setLog(e.getStackTrace().toString());
            e.printStackTrace();
        }//catch
        catch(java.io.IOException e){
            //Logger.setLog(e.getStackTrace().toString());
            e.printStackTrace();
        }//catch
        Node root = doc.getDocumentElement();
        return root;
    }
    public Node getRootNode(){return root;}
    public NodeList getTabNode(){return tabs;}
}
