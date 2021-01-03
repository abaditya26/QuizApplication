package com.aditya.quizapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aditya.quizapplication.AttemptQuizActivity;
import com.aditya.quizapplication.LoginActivity;
import com.aditya.quizapplication.Models.ModelOptions;
import com.aditya.quizapplication.NewQuizActivity;
import com.aditya.quizapplication.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterOptions extends RecyclerView.Adapter<AdapterOptions.MyHolder> {
    Context ctx;
    List<ModelOptions> optionsList;

    public AdapterOptions(Context ctx, List<ModelOptions> optionsList) {
        this.ctx = ctx;
        this.optionsList = optionsList;
    }

    @NonNull
    @Override
    public AdapterOptions.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.recycler_options,parent,false);
        return new AdapterOptions.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOptions.MyHolder holder, int position) {
        holder.setData(optionsList,position);
    }

    @Override
    public int getItemCount() {
        return optionsList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        CircleImageView icon;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.optionsTitle);
            description = itemView.findViewById(R.id.optionsDescription);
            icon = itemView.findViewById(R.id.iconOption);
        }

        public void setData(List<ModelOptions> optionsList, int position) {
            title.setText(optionsList.get(position).getTitle());
            description.setText(optionsList.get(position).getDescription());
            itemView.setOnClickListener(v -> {
                switch (optionsList.get(position).getTitle()){
                    case "Attempt Quiz":
                        Intent quiz = new Intent(ctx, AttemptQuizActivity.class);
                        quiz.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ctx.startActivity(quiz);
                        break;
                    case "New Quiz":
                        Intent newQuiz = new Intent(ctx, NewQuizActivity.class);
                        newQuiz.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ctx.startActivity(newQuiz);
                        break;
                    default:
                        Toast.makeText(ctx, "No Listener defined", Toast.LENGTH_SHORT).show();
                        break;
                }
            });
        }
    }
}
