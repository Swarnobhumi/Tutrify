package Model;

import java.util.ArrayList;

public class NestedQuestionModel {
    String subHeading, mainHeading;
    ArrayList<NestedNestedQuestionModel>arrayList;
    int subPic;




    public NestedQuestionModel(String subHeading, String mainHeading, int subPic, ArrayList<NestedNestedQuestionModel>arrayList) {
        this.subHeading = subHeading;
        this.mainHeading = mainHeading;
        this.subPic = subPic;
        this.arrayList = arrayList;

    }


    public ArrayList<NestedNestedQuestionModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<NestedNestedQuestionModel> arrayList) {
        this.arrayList = arrayList;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    public String getMainHeading() {
        return mainHeading;
    }

    public void setMainHeading(String mainHeading) {
        this.mainHeading = mainHeading;
    }

    public int getSubPic() {
        return subPic;
    }

    public void setSubPic(int subPic) {
        this.subPic = subPic;
    }
}
