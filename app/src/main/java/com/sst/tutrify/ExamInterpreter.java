package com.sst.tutrify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.aviran.cookiebar2.CookieBar;

import CustomComponents.LineNumberTextView;
import CustomComponents.SyntaxHighlightEditText;
import CustomComponents.UserProgressManager;

public class ExamInterpreter extends AppCompatActivity {
    TextView outPut, outputSection, givenCode;
    ImageView btnRun;
    SyntaxHighlightEditText codeView;
    LineNumberTextView lineNumberTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_interpreter);



        outPut = findViewById(R.id.outSectionExamYourself);
        btnRun = findViewById(R.id.btnPracticeCodeRunExam);
        codeView = findViewById(R.id.codeViewExam);
        outputSection = findViewById(R.id.outSectionExam);
        lineNumberTextView = findViewById(R.id.lineNumberTextViewExam);

        givenCode = findViewById(R.id.codeSectionExam);

        lineNumberTextView.setMainTextView(codeView);


        // initial the firebase realtime database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
        DatabaseReference nodeReference = databaseReference.child(GoogleSignIn.getLastSignedInAccount(ExamInterpreter.this).getId());




        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executePythonCode(codeView.getText().toString());

                if(outPut.getText().toString().trim().equals(outputSection.getText().toString().trim())){
                    UserProgressManager userProgressManager = new UserProgressManager();
                    userProgressManager.markQuestionAsSolved(ExamInterpreter.this, givenCode.getText().toString() );
                    CookieBar.build(ExamInterpreter.this)
                            .setTitle("Congratulation !")
                            .setMessage("You did it correctly !")
                            .setCookiePosition(CookieBar.BOTTOM)// Cookie will be displayed at the bottom
                            .setBackgroundColor(R.color.greenDialog)
                            .show();

                    nodeReference.child("certificateStatus").setValue(true);
                    nodeReference.child("examComplete").setValue(userProgressManager.getNumberOfSolvedQuestions(ExamInterpreter.this));

                    finish();
                    startActivity(new Intent(getApplicationContext(), CongratulationsActivity.class));

                }else{
                    CookieBar.build(ExamInterpreter.this)
                            .setTitle("Wrong Output !")
                            .setMessage("Please check your code ! The output of your code is not equal to expected output ! ")
                            .setCookiePosition(CookieBar.BOTTOM)// Cookie will be displayed at the bottom
                            .setBackgroundColor(R.color.redDialog)
                            .show();

                }

            }
        });


        codeView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void executePythonCode(String code){
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(ExamInterpreter.this));
        }
        Python py = Python.getInstance();
        PyObject pyObject = py.getModule("scripts").callAttr("main", code);
        outPut.setText(pyObject.toString());
    }
}