package Model;

import java.util.ArrayList;

public class DataModel {
    String mainHeading;
    ArrayList<NestedQuestionModel>list;
    int pic;

    public DataModel(String mainHeading, int pic,ArrayList<NestedQuestionModel> list) {
        this.mainHeading = mainHeading;
        this.list = list;
        this.pic = pic;
    }

    private boolean isExpanded;

    // Getter and setter for isExpanded
    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getMainHeading() {
        return mainHeading;
    }

    public void setMainHeading(String mainHeading) {
        this.mainHeading = mainHeading;
    }

    public ArrayList<NestedQuestionModel> getList() {
        return list;
    }

    public void setList(ArrayList<NestedQuestionModel> list) {
        this.list = list;
    }
}
