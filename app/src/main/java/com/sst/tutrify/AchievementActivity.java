package com.sst.tutrify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.opengl.Matrix;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.UserInformationModel;

public class AchievementActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
    LottieAnimationView firstQuestionLottie, fifthQuestionLottie, fifteenQuestionLottie, examLottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);
        firstQuestionLottie = findViewById(R.id.firstQuestionMedel);
        fifthQuestionLottie = findViewById(R.id.fifthQuestionMedel);
        fifteenQuestionLottie = findViewById(R.id.fifteenQuestionMedel);
        examLottie = findViewById(R.id.examLottie);


        databaseReference.child(GoogleSignIn.getLastSignedInAccount(AchievementActivity.this).getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserInformationModel userInformationModel = snapshot.getValue(UserInformationModel.class);
                if (userInformationModel.getPracticeComplete() > 0 && userInformationModel.getPracticeComplete() <= 4) {
                     setLottie(firstQuestionLottie, R.raw.first_practice);
                } else if (userInformationModel.getPracticeComplete()>=5 &&  userInformationModel.getPracticeComplete() <= 14) {
                     setLottie(firstQuestionLottie, R.raw.first_practice);
                     setLottie(fifthQuestionLottie, R.raw.fifth_practice_lottie);
                }else if (userInformationModel.getPracticeComplete() >=15){
                    setLottie(firstQuestionLottie, R.raw.first_practice);
                    setLottie(fifthQuestionLottie, R.raw.fifth_practice_lottie);
                    setLottie(fifteenQuestionLottie, R.raw.fifteen_question_solved);
                }

                if(userInformationModel.getExamComplete()>0){
                    setLottie(examLottie, R.raw.exam_trophy_lottie);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private  void setLottie(LottieAnimationView animationView, int rawRes){
        animationView.setAnimation(rawRes);
        animationView.setPadding(0, 0, 0, 0);
        animationView.playAnimation();
        animationView.setRepeatCount(LottieDrawable.INFINITE);
    }
}