package com.aditya.quizapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aditya.quizapplication.Models.ModelQuestions;
import com.aditya.quizapplication.R;

import java.util.List;

public class AdapterQuestions extends RecyclerView.Adapter<AdapterQuestions.ViewHolder> {

    Context ctx;
    List<ModelQuestions> questions;

    public AdapterQuestions(Context ctx, List<ModelQuestions> questions) {
        this.ctx = ctx;
        this.questions = questions;
    }

    @NonNull
    @Override
    public AdapterQuestions.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.recycler_question,parent,false);
        return new AdapterQuestions.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterQuestions.ViewHolder holder, int position) {
        holder.setData(questions, position);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setData(List<ModelQuestions> questions, int position) {
        }
    }
}
