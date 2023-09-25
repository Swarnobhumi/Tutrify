package com.sst.tutrify;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import CustomComponents.PdfGenerator;
import Model.UserInformationModel;


public class CertificateActivity extends AppCompatActivity {
    ConstraintLayout certificateLayout;
    Button btnDownload;
    TextView certificateText, textDate;
    String certificate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate);
        certificateLayout = findViewById(R.id.certificateLayout);
        btnDownload = findViewById(R.id.btnDownloadCertificate);
        certificateText = findViewById(R.id.certificateText);
        textDate = findViewById(R.id.textDate);



        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        reference.child(GoogleSignIn.getLastSignedInAccount(CertificateActivity.this).getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserInformationModel userInformationModel = snapshot.getValue(UserInformationModel.class);
                if(userInformationModel.isGirl()){
                    certificateText.setText(String.format("This is certify that Ms %s Succesfully passed the python exam.", userInformationModel.getCertificateName()));
                }else{
                    certificateText.setText(String.format("This is certify that Mr %s Succesfully passed the python exam.", userInformationModel.getCertificateName()));
                }
                certificate = userInformationModel.getCertificateName();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    PdfGenerator.generatePdf(CertificateActivity.this,certificateLayout, certificate+" certificate.pdf");

            }
        });




    }
}