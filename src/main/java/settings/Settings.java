package settings;

import gui.TabModel;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import static constants.StringsConstant.*;

public class Settings {
    public ArrayList <TabModel> getTabs (){
        XMLReader xmlReader  =  new XMLReader("E:\\IdeaProjects\\ConversionStatistic\\Project.xml");
        NodeList tabs = xmlReader.getTabNode();



        ArrayList <TabModel> tabModelsList = new ArrayList<>();

        int i =0;
        while (tabs!=null){
            String id =((Element)tabs.item(i)).getAttribute(ID);
            String folder =((Element)tabs.item(i)).getAttribute(FOLDER);
            String name =((Element)tabs.item(i)).getAttribute(NAME);
            String pair =((Element)tabs.item(i)).getAttribute(PAIR);
            tabModelsList.add(new TabModel(id, name, folder, pair));
            i++;
        }
        return tabModelsList;

    }
}
