package ExamQuestionData;

import com.sst.tutrify.R;

import java.util.ArrayList;

import Model.NestedNestedQuestionModel;
import Model.NestedQuestionModel;


public class Exam01Data {
    public static ArrayList<NestedQuestionModel> getExam01Question(){

        ArrayList<NestedNestedQuestionModel>nestedNestedSections = new ArrayList<>();
        nestedNestedSections.add(new NestedNestedQuestionModel(1, "(01) Make a variable and store a decimal value (5) now, write a python program to convert decimal to binary", "a = 5", "101"));


        ArrayList<NestedQuestionModel>arrayList = new ArrayList<>();
        arrayList.add(new NestedQuestionModel("Exam", "Decimal To Binary", R.drawable.reshot_icon_exam_t2cwlx5ra7, nestedNestedSections));

        return arrayList;
    }
}
