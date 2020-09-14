package com.naseyun.computer.ahchacha;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ListViewItem {

    private String quiz_title;
    private String answer_title;
    private String quizId;

    public void setQuiz(String quiz) {
        quiz_title = quiz;
    }

    public void setAnswer(String answer) {
        answer_title = answer;
    }

    public void setQuizId(String quizId) { this.quizId = quizId; }

    public String getQuiz() {
        return this.quiz_title;
    }

    public String getAnswer() {
        return this.answer_title;
    }

    public String getQuizId() { return quizId; }

    public ListViewItem(String quizId, String quiz, String answer) {
        this.quizId = quizId;
        quiz_title = quiz;
        answer_title = answer;
    }

    public ListViewItem() { }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        //result.put("id", quizId);
        result.put("quiz", quiz_title);
        result.put("answer", answer_title);
        return result;
    }
}
