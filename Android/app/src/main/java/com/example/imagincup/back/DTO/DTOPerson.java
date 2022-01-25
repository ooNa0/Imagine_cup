package com.example.imagincup.back.DTO;

import java.io.Serializable;

public class DTOPerson implements Serializable {

    private Integer PersonId; // PK
    private String PersonName; // 이름
    private Integer PersonDepressionScore; // 우울증 점수
    private String PersonDevice; // 디바이스 식별
    private Integer QuestionID;
    //private Integer ScoreID;

    public DTOPerson(Integer PersonId, String PersonName, Integer PersonDepressionScore, String PersonDevice, Integer QuestionID){
        this.PersonId = PersonId;
        this.PersonName = PersonName;
        this.PersonDepressionScore = PersonDepressionScore;
        this.PersonDevice = PersonDevice;
        this.QuestionID = QuestionID;
    }

    public DTOPerson(){}

    public Integer getPersonId(){
        return PersonId;
    }

    public String getPersonName(){
        return PersonName;
    }
    public void setPersonName(String PersonName){
        this.PersonName = PersonName;
    }

    public Integer getPersonDepressionScore(){
        return PersonDepressionScore;
    }
    public Integer getPersonDepressionPercent(){
        return PersonDepressionScore*5/3;
    }
    public void setPersonDepressionScore(Integer PersonDepressionScore){ this.PersonDepressionScore = PersonDepressionScore; }

    public String getPersonDevice(){
        return PersonDevice;
    }
    public void setPersonDevice(String PersonDevice){
        this.PersonDevice = PersonDevice;
    }

    public Integer getQuestionID(){
        return QuestionID;
    }
    public void setQuestionID(Integer QuestionID){ this.QuestionID = QuestionID; }
}
