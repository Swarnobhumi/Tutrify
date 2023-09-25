package com.sst.tutrify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserSmallInfoActivity extends AppCompatActivity {
    TextInputEditText userName;
    CheckBox accountNameCheck;
    RadioGroup radioGroup;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_small_info);

        userName = findViewById(R.id.userNameCertificate);
        accountNameCheck = findViewById(R.id.googleAccountCheck);
        radioGroup = findViewById(R.id.radioGroup2);
        btnConfirm = findViewById(R.id.btnConform);


        accountNameCheck.setText(GoogleSignIn.getLastSignedInAccount(UserSmallInfoActivity.this).getDisplayName());



        accountNameCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    userName.setText("");
                    userName.setEnabled(false);
                    if(radioGroup.getCheckedRadioButtonId()==R.id.maleRadio||radioGroup.getCheckedRadioButtonId()==R.id.femaleRadio){
                        btnConfirm.setEnabled(true);
                    }
                }else {
                    userName.setEnabled(true);
                    btnConfirm.setEnabled(false);
                }

            }
        });


        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    btnConfirm.setEnabled(false);
                }else {
                    if(radioGroup.getCheckedRadioButtonId()==R.id.maleRadio||radioGroup.getCheckedRadioButtonId()==R.id.femaleRadio) {
                        btnConfirm.setEnabled(true);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the login state true
                SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("login", true);
                editor.apply();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
                databaseReference.child(GoogleSignIn.getLastSignedInAccount(UserSmallInfoActivity.this).getId())
                        .child("certificateName")
                        .setValue(userName.getText().toString().equals("")?GoogleSignIn.getLastSignedInAccount(UserSmallInfoActivity.this).getDisplayName():userName.getText().toString());

                databaseReference.child(GoogleSignIn.getLastSignedInAccount(UserSmallInfoActivity.this).getId())
                        .child("girl")
                        .setValue(radioGroup.getCheckedRadioButtonId() != R.id.maleRadio);

                finish();
                startActivity(new Intent(UserSmallInfoActivity.this, MainPage.class));


            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
             if(!userName.getText().toString().equals("")||accountNameCheck.isChecked()){
                 btnConfirm.setEnabled(true);
             }

            }
        });

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Cannot go back from this stage !", Toast.LENGTH_SHORT).show();
    }

}