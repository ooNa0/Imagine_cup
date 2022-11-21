package com.example.imagincup.activity.survey;

import android.widget.RadioButton;
import android.widget.TextView;

public class Survey {

    private String question;
    private RadioButton btn1;
    private RadioButton btn2;
    private RadioButton btn3;
    private RadioButton btn4;
    private Integer score;

    public Survey(String question, RadioButton btn1, RadioButton btn2, RadioButton btn3, RadioButton btn4) {
        this.question = question;
        this.btn1 = btn1;
        this.btn2 = btn2;
        this.btn3 = btn3;
        this.btn4 = btn4;
    }

    public Survey(String question) {
        this.question = question;
    }

    public Integer getScore() { return score;}
    public void setScore(Integer score) { this.score = score;}

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public RadioButton getBtn1() {
        return btn1;
    }

    public void setBtn1(RadioButton btn1) {
        this.btn1 = btn1;
    }

    public RadioButton getBtn2() {
        return btn2;
    }

    public void setBtn2(RadioButton btn2) {
        this.btn2 = btn2;
    }

    public RadioButton getBtn3() {
        return btn3;
    }

    public void setBtn3(RadioButton btn3) {
        this.btn3 = btn3;
    }

    public RadioButton getBtn4() {
        return btn4;
    }

    public void setBtn4(RadioButton btn4) {
        this.btn4 = btn4;
    }
}
