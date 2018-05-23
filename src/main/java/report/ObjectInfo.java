package report;

public class ObjectInfo {
    private int testListNumber;
    private String category;
    private String name;
    private String report;
    private boolean fixed;
    private int build;
    private String tab;
    private String comment;

    public ObjectInfo(int testListNumber, String category, String name, String report){
        this.testListNumber=testListNumber;
        this.category=category;
        this.name=name;
        this.report=report;
    }
    public ObjectInfo(int testListNumber, String category, String name, String report, boolean fixed, int build, String tab, String comment){
        this.testListNumber=testListNumber;
        this.category=category;
        this.name=name;
        this.fixed = fixed;
        this.report = report;
        this.build = build;
        this.tab = tab;
        this.comment=comment;
    }

    public int getTestListNumber() {
        return testListNumber;
    }

    public void setTestListNumber(int testListNumber) {
        this.testListNumber = testListNumber;
    }

    public String getReport() {return report;}

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}

    public String getTab (){return tab;}

    public int getBuild () {return build;}

    public String getComment() { return comment; }

    public void setComment (String comment){this.comment=comment;}
}
