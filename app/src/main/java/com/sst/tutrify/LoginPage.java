package com.sst.tutrify;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.UserInformationModel;
import soup.neumorphism.NeumorphCardView;

public class LoginPage extends AppCompatActivity {
    NeumorphCardView btnLogin;
     GoogleSignInClient gsc;
     GoogleSignInOptions gso;
    private ActivityResultLauncher<Intent> startActivityResult;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");

//        Intent intent = new Intent(this, MainPage.class);
//        startActivity(intent);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);




        startActivityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK){
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                try {
                    task.getResult(ApiException.class);


                    databaseReference.child(GoogleSignIn.getLastSignedInAccount(getApplicationContext()).getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.exists()){
                                startActivity(new Intent(LoginPage.this, UserSmallInfoActivity.class));
                                GoogleSignInAccount signIn = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                                FirebaseDatabase.getInstance().getReference("User").child(signIn.getId()).setValue(new UserInformationModel(signIn.getId(), signIn.getDisplayName(), signIn.getEmail(), 0, 0, false, "", false));
                               finish();
                            }
                            else{
                                UserInformationModel model = snapshot.getValue(UserInformationModel.class);
                                assert model != null;
                                if(model.getCertificateName().equals("")){
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), UserSmallInfoActivity.class));
                                }else{
                                    SharedPreferences sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("login", true);
                                    editor.apply();
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), MainPage.class));


                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                } catch (ApiException e) {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     signIn();
            }
        });


    }
    private void signIn() {
        // Use the startActivityResult launcher
        Intent signInIntent = gsc.getSignInIntent();
        startActivityResult.launch(signInIntent);

        }




}