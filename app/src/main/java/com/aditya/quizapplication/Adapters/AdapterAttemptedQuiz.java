package com.aditya.quizapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aditya.quizapplication.Creator.OldQuestionsActivity;
import com.aditya.quizapplication.Models.ModelAttemptedQuiz;
import com.aditya.quizapplication.R;

import java.util.List;

public class AdapterAttemptedQuiz extends RecyclerView.Adapter<AdapterAttemptedQuiz.ViewHolder> {
    List<ModelAttemptedQuiz> quizList;
    Context ctx;

    public AdapterAttemptedQuiz(List<ModelAttemptedQuiz> quizList, Context ctx) {
        this.quizList = quizList;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public AdapterAttemptedQuiz.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.recycler_attempted_quiz,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAttemptedQuiz.ViewHolder holder, int position) {
        holder.setData(quizList,position);
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, id, score;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.quizCreatedName);
            id = itemView.findViewById(R.id.quizCreatedId);
            score = itemView.findViewById(R.id.scoreView);
        }

        public void setData(List<ModelAttemptedQuiz> quizList, int position) {
            title.setText("Name :- "+quizList.get(position).getQuizName());
            id.setText("ID :- "+quizList.get(position).getQuizId());
            score.setText("Score :- "+quizList.get(position).getScore()+" / "+quizList.get(position).getTotalQuestions());
        }
    }
}
