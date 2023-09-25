package QuestionData;

import java.util.ArrayList;

import Model.NestedNestedQuestionModel;

public class NestedNestedSection {

    public  static ArrayList<NestedNestedQuestionModel> getNested01_01(){
        ArrayList<NestedNestedQuestionModel>arrayList = new ArrayList<>();
        arrayList.add(new NestedNestedQuestionModel(1,"(01) We use print function to print or something", "print(\"hello world\")", "hello world"));
        arrayList.add(new NestedNestedQuestionModel(2,"(02) We can use comma for printing two values together", "print(\"hello\", \"world\")", "hello world"));
        return arrayList;

    }

    public  static ArrayList<NestedNestedQuestionModel> getNested02_01(){
        ArrayList<NestedNestedQuestionModel>arrayList = new ArrayList<>();
        arrayList.add(new NestedNestedQuestionModel(1,"(01) Write a Python program to print the sum of two digits.", "a = 5\nb = 8", "13"));
        arrayList.add(new NestedNestedQuestionModel(2,"(02) Write a Python program to calculate division of two digits and show the result in integer format.", "a = 8\nb=5", "1"));
        arrayList.add(new NestedNestedQuestionModel(3,"(03)  Write a Python program to calculate the reminder. ", "a = 10\nb = 3", "1"));
        return arrayList;
    }

    public  static ArrayList<NestedNestedQuestionModel> getNested02_02(){
        ArrayList<NestedNestedQuestionModel>arrayList = new ArrayList<>();
        arrayList.add(new NestedNestedQuestionModel(1,"(01) Write a Python program to print the sum of two digits and show the output in point value.", "a = 4.3\nb = 5.2", "9.5"));
        arrayList.add(new NestedNestedQuestionModel(2,"(02) Write a Python program to calculate division of two digits and show the result in two digits after decimal point", "a = 10\nb = 3", "3.33"));
       return arrayList;
    }


}
