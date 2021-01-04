package com.aditya.quizapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aditya.quizapplication.Models.ModelQuiz;
import com.aditya.quizapplication.R;

import java.util.List;

public class AdapterQuiz extends RecyclerView.Adapter<AdapterQuiz.ViewHolder> {

    List<ModelQuiz> quizList;
    Context ctx;

    public AdapterQuiz(List<ModelQuiz> quizList, Context ctx) {
        this.quizList = quizList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public AdapterQuiz.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.recycler_created_quiz,parent,false);
        return new AdapterQuiz.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterQuiz.ViewHolder holder, int position) {
        holder.setData(quizList,position);
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.quizCreatedName);
            id = itemView.findViewById(R.id.quizCreatedId);
        }

        public void setData(List<ModelQuiz> quizList, int position) {
            title.setText(quizList.get(position).getName());
            id.setText(quizList.get(position).getId());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
