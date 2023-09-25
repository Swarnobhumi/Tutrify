package QuestionData;

import com.sst.tutrify.R;

import java.util.ArrayList;

import Model.NestedQuestionModel;

public class SubSection {

    public static ArrayList<NestedQuestionModel> getSubQuestionsFirst(){
        ArrayList<NestedQuestionModel>nestedQuestionModels = new ArrayList<>();
        nestedQuestionModels.add(new NestedQuestionModel("Lesson 01", "Writing Code", R.drawable.baseline_menu_book_24,NestedNestedSection.getNested01_01()));
        return  nestedQuestionModels;
    }
    public static ArrayList<NestedQuestionModel> getSubQuestionsSecond(){
        ArrayList<NestedQuestionModel>nestedQuestionModels = new ArrayList<>();
        nestedQuestionModels.add(new NestedQuestionModel("Lesson 01", "Working With Integer", R.drawable.baseline_menu_book_24, NestedNestedSection.getNested02_01()));
        nestedQuestionModels.add(new NestedQuestionModel("Lesson 02", "Working With Float", R.drawable.baseline_menu_book_24, NestedNestedSection.getNested02_02()));
        return  nestedQuestionModels;
    }




}
