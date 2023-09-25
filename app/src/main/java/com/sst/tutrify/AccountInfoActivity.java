package com.sst.tutrify;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import Model.UserInformationModel;
import de.hdodenhof.circleimageview.CircleImageView;

public class AccountInfoActivity extends AppCompatActivity {
    TextView userNameInfo, userEmailInfo, showQuestionSloveNum, showExamSolve;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
    CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        userNameInfo = findViewById(R.id.userNameInfo);
        userEmailInfo = findViewById(R.id.userEmailInfo);
        showQuestionSloveNum = findViewById(R.id.showQuestionSolveNumber);
        showExamSolve = findViewById(R.id.showExamSolveNumber);
        circleImageView = findViewById(R.id.userInfoPic);

        databaseReference.child(Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(getApplicationContext()).getId())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                   UserInformationModel user = dataSnapshot.getValue(UserInformationModel.class);
                    assert user != null;
                         userNameInfo.setText(user.getUserName());
                         userEmailInfo.setText(user.getUserGmail());
                         showQuestionSloveNum.setText(String.valueOf(user.getPracticeComplete()));
                         showExamSolve.setText(String.valueOf(user.getExamComplete()));

                          Picasso.get().load(GoogleSignIn.getLastSignedInAccount(getApplicationContext()).getPhotoUrl()).into(circleImageView);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors here
            }
        });
    }
}