package com.sst.tutrify;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Adapter.QuestionRecyelerViewAdapter;
import Model.DataModel;
import QuestionData.SubSection;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainPage extends AppCompatActivity {
    ImageView openDrawable;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView recyclerView;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);



        SharedPreferences sharedPreferences = getSharedPreferences("Exam", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isExam", false);
        editor.apply();

        openDrawable = findViewById(R.id.open_drawer);
        drawerLayout = findViewById(R.id.drawable_layout);
        navigationView = findViewById(R.id.nav_layout);
        recyclerView = findViewById(R.id.recyclerViewMain);


        View headerView = navigationView.getHeaderView(0);
        TextView userEmail = headerView.findViewById(R.id.userEmail);
        TextView userName = headerView.findViewById(R.id.userName);
        CircleImageView userPic = headerView.findViewById(R.id.userProfilePic);


        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            if (signInAccount.getEmail().length() >= 15) {
                userEmail.setText(signInAccount.getEmail().substring(0, 15) + "....");
            } else {
                userEmail.setText(signInAccount.getEmail());
            }
            Picasso.get().load(signInAccount.getPhotoUrl()).into(userPic);
            userName.setText("Welcome, \n" + signInAccount.getDisplayName());
        } else {
            userEmail.setText("test@gmail.com");
            userName.setText("Welcome, \n" + "Devoloper");
            userPic.setImageResource(R.drawable.introduction_recycler);
        }


        openDrawable.setOnClickListener(view -> {
            drawerLayout.setDrawerElevation(0.5f);
            drawerLayout.open();
            navigationView.bringToFront();
            navigationView.setCheckedItem(R.id.item_python);
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item_confution) {
                    Intent intent = new Intent(getApplicationContext(), Chatgpt.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.item_Exam) {
                    SharedPreferences sharedPreferences = getSharedPreferences("Exam", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isExam", true);
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), ExamActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.item_Complier) {
                    Intent intent = new Intent(getApplicationContext(), ComplierPage.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.item_Logout) {
                    new FancyGifDialog.Builder(MainPage.this)
                            .setTitle("Confirm") // You can also send title like R.string.from_resources
                            .setMessage("Are you sure to want to Logout ?") // or pass like R.string.description_from_resources
                            .setTitleTextColor(R.color.statusBg)
                            .setDescriptionTextColor(R.color.statusBg)
                            .setNegativeBtnText("Cancel") // or pass it like android.R.string.cancel
                            .setPositiveBtnBackground(R.color.greenDialog)
                            .setPositiveBtnText("Ok") // or pass it like android.R.string.ok
                            .setNegativeBtnBackground(R.color.redDialog)
                            .setGifResource(R.drawable.question_dialog)   //Pass your Gif here
                            .isCancellable(true)
                            .OnPositiveClicked(() -> {
                                // Save the login state to false because the user logout
                                gsc.signOut();
                                SharedPreferences sharedPreferences1 = getSharedPreferences("Login", MODE_PRIVATE);
                                SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                                editor1.putBoolean("login", false);
                                editor1.apply();

                                startActivity(new Intent(MainPage.this, LoginPage.class));
                                finish();
                            })
                            .OnNegativeClicked(() -> {

                            })
                            .build();
                } else if (item.getItemId()==R.id.item_account_info) {
                    startActivity(new Intent(MainPage.this, AccountInfoActivity.class));
                } else if (item.getItemId()==R.id.item_settings) {
                    startActivity(new Intent(MainPage.this, SettingsActivity.class));
                } else if (item.getItemId()==R.id.item_achievement) {
                    startActivity(new Intent(MainPage.this, AchievementActivity.class));
                } else if (item.getItemId() == R.id.item_tutorial) {
                    startActivity(new Intent(MainPage.this, TutorialActivity.class));
                }


                return true;
            }
        });



        ArrayList<DataModel> dataModels = new ArrayList<>();
        dataModels.add(new DataModel("Introduction to python", R.drawable.introduction_recycler, SubSection.getSubQuestionsFirst()));
        dataModels.add(new DataModel("Datatypes & Variables", R.drawable.reshot_icon_database_architecture_39dm8ynuca, SubSection.getSubQuestionsSecond()));
        //dataModels.add(new DataModel("Taking Decisions", R.drawable.decision_icon, SubSection.getSubQuestionsSecond()));

        QuestionRecyelerViewAdapter adapter = new QuestionRecyelerViewAdapter(dataModels, MainPage.this, MainPage.this);


        LinearLayoutManager manager = new LinearLayoutManager(MainPage.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isOpen()) {
            drawerLayout.close();
        } else {
            new FancyGifDialog.Builder(this)
                    .setTitle("Confirm") // You can also send title like R.string.from_resources
                    .setMessage("Are you sure to want to quit this app ?") // or pass like R.string.description_from_resources
                    .setTitleTextColor(R.color.statusBg)
                    .setDescriptionTextColor(R.color.statusBg)
                    .setNegativeBtnText("Cancel") // or pass it like android.R.string.cancel
                    .setPositiveBtnBackground(R.color.greenDialog)
                    .setPositiveBtnText("Ok") // or pass it like android.R.string.ok
                    .setNegativeBtnBackground(R.color.redDialog)
                    .setGifResource(R.drawable.question_dialog)   //Pass your Gif here
                    .isCancellable(true)
                    .OnPositiveClicked(() -> finish())
                    .OnNegativeClicked(() -> {

                    })
                    .build();

        }
    }


}