package com.sst.tutrify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapter.QuestionRecyelerViewAdapter;
import ExamQuestionData.Exam01Data;
import Model.DataModel;
import Model.UserInformationModel;


public class ExamActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView btnCertificate;
    TextView  certificateStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        recyclerView = findViewById(R.id.recyclerViewExam);
        btnCertificate = findViewById(R.id.btnCertificate);
        certificateStatus = findViewById(R.id.certificateStatus);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.child(GoogleSignIn.getLastSignedInAccount(ExamActivity.this).getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserInformationModel userInformationModel = snapshot.getValue(UserInformationModel.class);
                if(userInformationModel.isCertificateStatus()){
                    ColorMatrix matrix = new ColorMatrix();
                    matrix.setSaturation(1);
                    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                    btnCertificate.setColorFilter(filter);
                    certificateStatus.setText("Your certificate is ready ! Click on the certificate button to download");
                    btnCertificate.setEnabled(true);
                }else{
                    ColorMatrix matrix = new ColorMatrix();
                    matrix.setSaturation(0);
                    ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                    btnCertificate.setColorFilter(filter);
                    certificateStatus.setText("Your certificate is close");
                    btnCertificate.setEnabled(false);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        ArrayList<DataModel> dataModels = new ArrayList<>();
        dataModels.add(new DataModel("Exam 01", R.drawable.introduction_recycler, Exam01Data.getExam01Question()));
        //dataModels.add(new DataModel("Taking Decisions", R.drawable.decision_icon, SubSection.getSubQuestionsSecond()));

        QuestionRecyelerViewAdapter adapter = new QuestionRecyelerViewAdapter(dataModels, ExamActivity.this, ExamActivity.this);


        LinearLayoutManager manager = new LinearLayoutManager(ExamActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);


        btnCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ExamActivity.this, CertificateActivity.class));

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences sharedPreferences = getSharedPreferences("Exam", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isExam", false);
        editor.apply();

    }


}