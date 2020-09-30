package ltd.icecold.paidcommand;

import java.util.List;

public class PaidBean {

    /**
     * name : gamemode
     * type : points
     * cost : 100
     * isOp : true
     * ignore : ["ice-cold"]
     */

    private String name;
    private String type;
    private int cost;
    private boolean isOp;
    private List<String> ignore;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isIsOp() {
        return isOp;
    }

    public void setIsOp(boolean isOp) {
        this.isOp = isOp;
    }

    public List<String> getIgnore() {
        return ignore;
    }

    public void setIgnore(List<String> ignore) {
        this.ignore = ignore;
    }
}
