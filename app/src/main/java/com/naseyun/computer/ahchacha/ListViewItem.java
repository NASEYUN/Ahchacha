package com.naseyun.computer.ahchacha;

public class ListViewItem {

    private String quiz_title;
    private String answer_title;

    public void setQuiz(String quiz) {
        quiz_title = quiz;
    }

    public void setAnswer(String answer) {
        answer_title = answer;
    }

    public String getQuiz() {
        return this.quiz_title;
    }

    public String getAnswer() {
        return this.answer_title;
    }
}
