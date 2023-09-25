package Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sst.tutrify.R;

import java.util.ArrayList;

import Model.DataModel;
import de.hdodenhof.circleimageview.CircleImageView;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class QuestionRecyelerViewAdapter extends RecyclerView.Adapter<QuestionRecyelerViewAdapter.viewHolder> {
Context context;
Activity activity;

ArrayList<DataModel>dataModels;


    public QuestionRecyelerViewAdapter(ArrayList<DataModel>dataModels,Context context, Activity activity) {
        this.dataModels = dataModels;
        this.context = context;
        this.activity = activity;

    }



    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.question_recycler_view_design, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (dataModels.get(position).isExpanded()) {
            // Show additional content or views
            holder.nestedRecyclerView.setVisibility(View.VISIBLE);
            holder.arrow.setImageResource(R.drawable.baseline_arrow_circle_up_24);
        } else {
            // Hide additional content or views
            holder.nestedRecyclerView.setVisibility(View.GONE);
            holder.arrow.setImageResource(R.drawable.baseline_expand_circle_down_24);
        }

              holder.moduleText.setText("Module "+ String.valueOf(position+1));
              holder.imageView.setImageResource(dataModels.get(position).getPic());
              holder.headerText.setText(dataModels.get(position).getMainHeading());

              LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.itemView.getContext());
              NestedRecyclerViewAdapter adapter = new NestedRecyclerViewAdapter(dataModels.get(position).getList(),context);
              holder.nestedRecyclerView.setLayoutManager(linearLayoutManager);
              holder.nestedRecyclerView.setHasFixedSize(true);
              holder.nestedRecyclerView.setAdapter(adapter);


        View decorView = activity.getWindow().getDecorView();
        // ViewGroup you want to start blur from. Choose root as close to BlurView in hierarchy as possible.
        ViewGroup rootView = decorView.findViewById(android.R.id.content);

        // Optional:
        // Set drawable to draw in the beginning of each blurred frame.
        // Can be used in case your layout has a lot of transparent space and your content
        // gets a too low alpha value after blur is applied.
        Drawable windowBackground = decorView.getBackground();

        holder.blurView.setupWith(rootView, new RenderScriptBlur(context)) // or RenderEffectBlur
                .setFrameClearDrawable(windowBackground) // Optional
                .setBlurRadius(20f);

        holder.blurView .setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        holder.blurView .setClipToOutline(true);

        holder.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                DataModel item = dataModels.get(position);

                // Toggle the expanded state
                item.setExpanded(!item.isExpanded());

                // Notify the adapter of the change
                notifyItemChanged(position);




            }
        });



    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
       CircleImageView imageView;
       TextView headerText, moduleText;
       ImageView arrow;
       BlurView blurView;
       RecyclerView nestedRecyclerView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.mainItemPic);
            headerText = itemView.findViewById(R.id.mainItemHeading);
            arrow = itemView.findViewById(R.id.btnArrow);
            nestedRecyclerView = itemView.findViewById(R.id.nestedRecyclerView);
            blurView = itemView.findViewById(R.id.blurViewQuestion);
            moduleText = itemView.findViewById(R.id.moduleName);

        }

    }


}
