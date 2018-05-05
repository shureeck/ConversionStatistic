package settings;

import gui.TabModel;
import javafx.scene.control.Tab;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import static constants.StringsConstant.*;

public class Settings {
    private ArrayList <TabModel> tabModelsList = new ArrayList<>();
    private ArrayList <Tab> tabList = new ArrayList<>();
    private String projectPath = PROJECT_PATH;

    public Settings(){
        this.tabModelsList.addAll(fillTabModelsList(PROJECT_PATH));
        this.tabList.addAll(fillTabList(tabModelsList));
    }

    private ArrayList <TabModel> fillTabModelsList (String path){
        XMLReader xmlReader  =  new XMLReader(path);
        NodeList tabs = xmlReader.getTabNode();

        ArrayList <TabModel> tabModelsList = new ArrayList<>();

        int i =0;
        while (i<tabs.getLength()){
            String id =((Element)tabs.item(i)).getAttribute(ID);
            String folder =((Element)tabs.item(i)).getAttribute(FOLDER);
            String name =((Element)tabs.item(i)).getAttribute(NAME);
            String pair =((Element)tabs.item(i)).getAttribute(PAIR);
            tabModelsList.add(new TabModel(id, name, folder, pair));
            i++;
        }
        return this.tabModelsList=tabModelsList;
    }

    private ArrayList<Tab> fillTabList(ArrayList<TabModel> tabModelsList){
        ArrayList<Tab> tabList = new ArrayList<>();
        int i=0;
        while (i<tabModelsList.size()){
            tabList.add(tabModelsList.get(i).getTab());
            i++;
        }
        return tabList;
    }

    public ArrayList<Tab> getTabList() {
        return tabList;
    }

    public void addTabModel(TabModel tabModel){
        tabModelsList.add(tabModel);
        tabList.add(tabModel.getTab());
        XMLReader reader = new XMLReader(projectPath);
        reader.setTab(tabModel);
    }

    public ArrayList<TabModel> getTabModelsList(){return tabModelsList;}

}
