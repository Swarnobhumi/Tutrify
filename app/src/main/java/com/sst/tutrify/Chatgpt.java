package com.sst.tutrify;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;
import java.util.List;

import Adapter.ChatMessageAdapter;
import Model.ApiResponse;
import Model.ApiService;
import Model.ChatMessageModel;
import Model.SearchResult;
import Model.WikipediaApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chatgpt extends AppCompatActivity {
    LottieAnimationView btnSend;
    EditText messageBox;
    List<ChatMessageModel> list;
    ChatMessageAdapter chatMessageAdapter;
    RecyclerView recyclerView;
    GoogleSignInAccount signInAccount;
    private WikipediaApi wikipediaApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatgpt);


        btnSend = findViewById(R.id.btn_send);
        messageBox = findViewById(R.id.message_box);
        recyclerView = findViewById(R.id.recyclerView);

        signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        list = new ArrayList<>();


        if(signInAccount!=null){
            chatMessageAdapter = new ChatMessageAdapter(list, this, getApplicationContext(), signInAccount.getPhotoUrl());
        }else{
            chatMessageAdapter = new ChatMessageAdapter(list, this, getApplicationContext(), null);
        }


        wikipediaApi = ApiService.getRetrofitInstance().create(WikipediaApi.class);


        recyclerView.setAdapter(chatMessageAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayoutManager);





        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                btnSend.setEnabled(false);
                btnSend.playAnimation();

                btnSend.addAnimatorListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        btnSend.setEnabled(true);
                        btnSend.setProgress(0);

                    }
                });

                String result = messageBox.getText().toString();

                addChart(result, ChatMessageModel.SEND_BY_ME);
                messageBox.setText("");



                Call<ApiResponse> call = wikipediaApi.search(result);

                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.isSuccessful()) {
                            ApiResponse apiResponse = response.body();
                            List<SearchResult> searchResults = apiResponse.getQueryResult().getSearchResults();


                            StringBuilder resultBuilder = new StringBuilder();

                            for (SearchResult searchResult : searchResults) {
                                resultBuilder.append(searchResult.title + ":" + searchResult.snippet + "\n").append("\n");

                            }


                            addChart(removeHtmlTags(resultBuilder.toString()), ChatMessageModel.SEND_BY_BOT);

                        } else {
                            Toast.makeText(Chatgpt.this, "Not", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        // Handle network or API call failure'
                        Toast.makeText(Chatgpt.this, "fg", Toast.LENGTH_SHORT).show();
                    }
                });


            }

        });

    }


    void addChart(String message, String sendBy) {
        runOnUiThread(new Runnable() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void run() {
                list.add(new ChatMessageModel(message, sendBy));
                chatMessageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(chatMessageAdapter.getItemCount());
            }
        });

    }

    public static String removeHtmlTags(String html) {
        // Remove HTML tags using regular expressions
        return html.replaceAll("\\<.*?\\>", "");
    }



}







