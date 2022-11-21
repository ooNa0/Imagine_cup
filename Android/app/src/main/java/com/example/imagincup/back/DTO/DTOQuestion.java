package com.example.imagincup.back.DTO;

import java.io.Serializable;

public class DTOQuestion implements Serializable {

    private Integer QuestionId; // PK
    private String QuestionText; // 질문
    private Integer SurveyID; // 해당 질문지 번호 1~20

    public DTOQuestion(Integer QuestionId, String QuestionText, Integer SurveyID){
        this.QuestionId = QuestionId;
        this.QuestionText = QuestionText;
        this.SurveyID = SurveyID;
    }

    public Integer getQuestionId(){
        return QuestionId;
    }

    public String getQuestionText(){
        return QuestionText;
    }
    public void setQuestionText(String QuestionText){
        this.QuestionText = QuestionText;
    }

    public Integer getSurveyID(){
        return SurveyID;
    }
    public void setSurveyID(Integer SurveyID){ this.SurveyID = SurveyID; }
}
