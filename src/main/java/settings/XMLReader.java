package settings;

import gui.TabModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;

import static constants.StringsConstant.*;

public class XMLReader {

    private Node root;
    private Node databse;
    private NodeList tabs;
    private String path;

   public XMLReader(String path){
       this.path=path;
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

    public void setTab(TabModel tabModel){
        NodeList categoriesList = ((Element) getRootNode()).getElementsByTagName(CATEGORY);
        Node categotyTabs = getCatgoryTabs(categoriesList);
        Element tabEleent = buildTab(tabModel);
        Node childNode = categotyTabs.getOwnerDocument().importNode(tabEleent, true);
        categotyTabs.appendChild(childNode);
        writeXML(categotyTabs.getOwnerDocument().getDocumentElement(), path);
    }


    private Node getCatgoryTabs(NodeList categoriesList){
        Node categoryTabs = null;
        int i =0;
        while (i<categoriesList.getLength()){
            if(((Element) categoriesList.item(i)).getAttribute(NAME).equals(TABS)){
                categoryTabs=categoriesList.item(i);
                break;
            }
            i++;
        }
        return  categoryTabs;
    }

    private Element buildTab(TabModel tabModel) {
        Document doc=null;
        try {
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.newDocument();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Element tabElement = doc.createElement(TAB);
        tabElement.setAttribute(ID, tabModel.getTabId());
        tabElement.setAttribute(NAME, tabModel.getTabName());
        tabElement.setAttribute(FOLDER, tabModel.getReportFolder());
        tabElement.setAttribute(PAIR, tabModel.getPair());

        return tabElement;
    }

    private void writeXML(Node root, String path){
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSourcee = new DOMSource(root.getOwnerDocument());

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "XML");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            StreamResult result = new StreamResult(new File(path));
            transformer.transform(domSourcee, result);
        }
        catch (TransformerException e){
            e.printStackTrace();
        }
    }
}
