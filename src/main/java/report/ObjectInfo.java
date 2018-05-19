package report;

public class ObjectInfo {
    private int testListNumber;
    private String category;
    private String name;
    private String report;

    public ObjectInfo(int testListNumber, String category, String name, String report){
        this.testListNumber=testListNumber;
        this.category=category;
        this.name=name;
        this.report=report;
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

    public void setName(String name) {
        this.name = name;
    }
}
