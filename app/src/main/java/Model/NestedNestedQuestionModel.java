package Model;

public class NestedNestedQuestionModel {
  private  String questionName, questionCode, questionOutput;
  private int questionNumber;
    public NestedNestedQuestionModel(int questionNumber, String questionName, String questionCode, String questionOutput) {
        this.questionName = questionName;
        this.questionCode = questionCode;
        this.questionOutput = questionOutput;
        this.questionNumber = questionNumber;
    }


    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getQuestionOutput() {
        return questionOutput;
    }

    public void setQuestionOutput(String questionOutput) {
        this.questionOutput = questionOutput;
    }
}
