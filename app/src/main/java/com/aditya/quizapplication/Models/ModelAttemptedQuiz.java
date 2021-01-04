package com.aditya.quizapplication.Models;

public class ModelAttemptedQuiz {
    private String quizId, quizName, totalQuestions, score, uid;

    public ModelAttemptedQuiz() {
    }

    public ModelAttemptedQuiz(String quizId, String quizName, String totalQuestions, String score, String uid) {
        this.quizId = quizId;
        this.quizName = quizName;
        this.totalQuestions = totalQuestions;
        this.score = score;
        this.uid = uid;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(String totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
