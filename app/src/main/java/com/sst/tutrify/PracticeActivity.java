package com.sst.tutrify;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.aviran.cookiebar2.CookieBar;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import CustomComponents.LineNumberTextView;
import CustomComponents.SyntaxHighlightEditText;
import CustomComponents.UserProgressManager;
import Model.NestedNestedQuestionModel;

public class PracticeActivity extends AppCompatActivity {

    ImageView btnRun;
    TextView outPut, outPutSection;
    TextView questionName, codeSection;
    TextView lessonNum, lessonName;
     SyntaxHighlightEditText codeView;
     LineNumberTextView lineNumberTextView;
    Button btnNext;
    int currentQuestionIndex = 0;
    ArrayList<NestedNestedQuestionModel> questionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        btnRun = findViewById(R.id.btnPracticeCodeRun);
        outPut = findViewById(R.id.yourCodeoutSection);
        codeView = findViewById(R.id.codeView);
        lineNumberTextView = findViewById(R.id.lineNumberTextView);
        outPutSection = findViewById(R.id.outSection);

        btnNext = findViewById(R.id.btnNextQuestion);

        questionName = findViewById(R.id.questionName);
        codeSection = findViewById(R.id.codeSection);

        lessonName = findViewById(R.id.leassonName);
        lessonNum = findViewById(R.id.leassonNo);


        // initial the firebase realtime database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
        DatabaseReference nodeReference = databaseReference.child(GoogleSignIn.getLastSignedInAccount(PracticeActivity.this).getId());



        // Here are the pairComplete map
        Map<Character, Character> pairCompleteMap = new HashMap<>();
        pairCompleteMap.put('{', '}');
        pairCompleteMap.put('[', ']');
        pairCompleteMap.put('(', ')');
        pairCompleteMap.put('<', '>');
        pairCompleteMap.put('"', '"');
        pairCompleteMap.put('\'', '\'');


        // initial codeView
        codeView.setPairCompleteMap(pairCompleteMap);
        codeView.enablePairComplete(true);
        codeView.enablePairCompleteCenterCursor(true);


        lineNumberTextView.setMainTextView(codeView);

        //Receiving the string extra
        Intent intent = getIntent();
        lessonNum.setText(intent.getStringExtra("lessonNo"));
        lessonName.setText(intent.getStringExtra("lessonName"));


        // Get the arraylist from previous activity
        SharedPreferences sharedPreferences = getSharedPreferences("questionsList", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("dataListJson", "");

        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<NestedNestedQuestionModel>>() {}.getType();
        questionList = gson.fromJson(json, listType);


        // setUp the questions for the first time
        questionName.setText(questionList.get(currentQuestionIndex).getQuestionName());
        codeSection.setText(questionList.get(currentQuestionIndex).getQuestionCode());
        outPutSection.setText(questionList.get(currentQuestionIndex).getQuestionOutput());


        questionName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    btnNext.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executePythonCode(codeView.getText().toString());
            }

        });



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionIndex++;
                if (currentQuestionIndex < questionList.size()) {
                    displayQuestion(currentQuestionIndex);
                } else {
                    finish();
                }
            }
        });



        outPut.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("ResourceType")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(outPut.getText().toString().trim().equals(outPutSection.getText().toString().trim())&& codeView.getLineCount()>1){
                  UserProgressManager userProgressManager = new UserProgressManager();
                  userProgressManager.markQuestionAsSolved(PracticeActivity.this, codeSection.getText().toString());

                    CookieBar.build(PracticeActivity.this)
                            .setTitle("Congratulation !")
                            .setMessage("You did it correctly !")
                            .setCookiePosition(CookieBar.BOTTOM)// Cookie will be displayed at the bottom
                            .setBackgroundColor(R.color.greenDialog)
                            .show();
                    btnNext.setEnabled(true);

                    nodeReference.child("practiceComplete").setValue(userProgressManager.getNumberOfSolvedQuestions(PracticeActivity.this));

                }else{
                    CookieBar.build(PracticeActivity.this)
                            .setTitle("Wrong Output !")
                            .setMessage("Please check your code ! The output of your code is not equal to expected output ! ")
                            .setCookiePosition(CookieBar.BOTTOM)// Cookie will be displayed at the bottom
                            .setBackgroundColor(R.color.redDialog)
                            .show();
                    btnNext.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }
    private void executePythonCode(String code){
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(PracticeActivity.this));
        }
        Python py = Python.getInstance();
        PyObject pyObject = py.getModule("scripts").callAttr("main", code);
        outPut.setText(pyObject.toString());
    }

    private void displayQuestion(int index) {
        if (index < questionList.size()) {
            questionName.setText(questionList.get(index).getQuestionName());
            codeSection.setText(questionList.get(index).getQuestionCode());
            outPutSection.setText(questionList.get(index).getQuestionOutput());
        }

    }
}