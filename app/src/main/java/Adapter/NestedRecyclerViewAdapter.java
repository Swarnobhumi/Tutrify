package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sst.tutrify.ExamInterpreter;
import com.sst.tutrify.PracticeActivity;
import com.sst.tutrify.R;

import java.util.ArrayList;

import Model.NestedQuestionModel;

public class NestedRecyclerViewAdapter extends RecyclerView.Adapter<NestedRecyclerViewAdapter.ViewClass> {
    ArrayList<NestedQuestionModel>list;
    Context context;
     String currentLessonNum;

    public NestedRecyclerViewAdapter(ArrayList<NestedQuestionModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.nested_recyclerview_design, parent, false);
        return new ViewClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewClass holder, @SuppressLint("RecyclerView") int position) {


        holder.subHeadingText.setText(list.get(position).getSubHeading());
        holder.mainHeadingText.setText(list.get(position).getMainHeading());
        holder.subItemImageView.setImageResource(list.get(position).getSubPic());
        holder.itemView.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left));

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLessonNum = holder.subHeadingText.getText().toString();
                Gson gson = new Gson();
                String json = gson.toJson(list.get(position).getArrayList());

                SharedPreferences sharedPreferences = context.getSharedPreferences("questionsList", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("dataListJson", json);
                editor.apply();

                SharedPreferences sharedPreferences1 = context.getSharedPreferences("Exam", Context.MODE_PRIVATE);
                if(sharedPreferences1.getBoolean("isExam", false)){
                    Intent intent = new Intent(context, ExamInterpreter.class);
                    intent.putExtra("lessonNo", holder.subHeadingText.getText().toString());
                    intent.putExtra("lessonName", holder.mainHeadingText.getText().toString());
                    context.startActivity(intent);
                }else{
                    Intent intent = new Intent(context, PracticeActivity.class);
                    intent.putExtra("lessonNo", holder.subHeadingText.getText().toString());
                    intent.putExtra("lessonName", holder.mainHeadingText.getText().toString());
                    context.startActivity(intent);
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewClass extends RecyclerView.ViewHolder {
        TextView subHeadingText, mainHeadingText;
        ImageView subItemImageView;
        Button button;
        public ViewClass(@NonNull View itemView) {
            super(itemView);
            subHeadingText = itemView.findViewById(R.id.subItemHeading);
            mainHeadingText = itemView.findViewById(R.id.subItemName);
            subItemImageView = itemView.findViewById(R.id.subItemImage);
            button = itemView.findViewById(R.id.btnOpen);

        }
    }
}
