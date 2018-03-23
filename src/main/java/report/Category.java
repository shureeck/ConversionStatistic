package report;

public class Category {
    private String name;
    private int passed;
    private int failed;
    private int count;

    public Category(String name, int passed, int failed){
        this.name=name;
        this.passed=passed;
        this.failed=failed;
        this.count=passed+failed;
    }

    public Category(String name, int count){
        this.name=name;
        this.count=count;
        this.failed=0;
        this.passed=0;
    }

    public int getPassed() {
        return passed;
    }
    public void setPassed(int passed) {
        this.passed = passed;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getFailed() {
        return failed;
    }
    public void setFailed(int failed) {
        this.failed = failed;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
