package Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.sst.tutrify.R;

import java.util.List;

import Model.ChatMessageModel;
import de.hdodenhof.circleimageview.CircleImageView;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ChatGptHolder> {

    List<ChatMessageModel>list;

    Activity activity;
    Context context;
    Uri uri;

    public ChatMessageAdapter(List<ChatMessageModel> list, Activity activity, Context context, Uri uri) {
        this.list = list;
        this.activity = activity;
        this.context = context;
        this.uri = uri;
    }



    @NonNull
    @Override
    public ChatGptHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_design, parent, false);
        return new ChatGptHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatGptHolder holder, int position) {

        float radius = 20f;

        View decorView = activity.getWindow().getDecorView();
        // ViewGroup you want to start blur from. Choose root as close to BlurView in hierarchy as possible.
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);

        // Optional:
        // Set drawable to draw in the beginning of each blurred frame.
        // Can be used in case your layout has a lot of transparent space and your content
        // gets a too low alpha value after blur is applied.
        Drawable windowBackground = decorView.getBackground();

        holder.blurView.setupWith(rootView, new RenderScriptBlur(context)) // or RenderEffectBlur
                .setFrameClearDrawable(windowBackground) // Optional
                .setBlurRadius(radius);

        holder.blurView .setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        holder.blurView .setClipToOutline(true);


        holder.blurView1.setupWith(rootView, new RenderScriptBlur(context)) // or RenderEffectBlur
                .setFrameClearDrawable(windowBackground) // Optional
                .setBlurRadius(radius);

        holder.blurView1.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        holder.blurView1.setClipToOutline(true);


        if(uri!=null){
            Picasso.get().load(uri).into(holder.circleImageView);
        }else{
            holder.circleImageView.setImageResource(R.drawable.introduction_recycler);
        }



               ChatMessageModel messageModel = list.get(position);
               if(messageModel.getSendBy().equals(ChatMessageModel.SEND_BY_ME)){
                   holder.sendBody.setVisibility(View.VISIBLE);
                   holder.receivedBody.setVisibility(View.GONE);
                   holder.sendText.setText(messageModel.getMessage());
               }else{
                   holder.sendBody.setVisibility(View.GONE);
                   holder.receivedBody.setVisibility(View.VISIBLE);
                   holder.receiveText.setText(messageModel.getMessage());
               }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public  class ChatGptHolder extends RecyclerView.ViewHolder{

        LinearLayout sendBody, receivedBody;
        TextView sendText, receiveText;
        BlurView blurView, blurView1;
        CircleImageView circleImageView;

        public ChatGptHolder(@NonNull View itemView) {
            super(itemView);
            sendBody = itemView.findViewById(R.id.send_body);
            receivedBody = itemView.findViewById(R.id.receiveBody);
            sendText = itemView.findViewById(R.id.chat_send_text);
           receiveText = itemView.findViewById(R.id.receive_text);
           blurView = itemView.findViewById(R.id.blurView);
           blurView1 = itemView.findViewById(R.id.blurView1);
           circleImageView = itemView.findViewById(R.id.userDp);
        }
    }

}
