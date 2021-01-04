package com.aditya.quizapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        TextView questionTextView, answerTextView;
        Button editBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            answerTextView  = itemView.findViewById(R.id.answerTextView);
            editBtn = itemView.findViewById(R.id.btnEdit);
        }

        public void setData(List<ModelQuestions> questions, int position) {
            questionTextView.setText(questions.get(position).getQuestion());
            answerTextView.setText(questions.get(position).getAnswer());
            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ctx, "Edit Button CLicked", Toast.LENGTH_SHORT).show();
                    //edit button code
                }
            });
        }
    }
}
