package com.example.imagincup.back;

public class DTOPerson {

    private Integer PersonId;
    private String PersonName;
    private Integer PersonDepressionScore;
    private String PersonDevice;
    private Integer RecordID;
    private Integer ScoreID;

    public DTOPerson(Integer PersonId, String PersonName, Integer PersonDepressionScore, String PersonDevice, Integer RecordID){
        this.PersonId = PersonId;
        this.PersonName = PersonName;
        this.PersonDepressionScore = PersonDepressionScore;
        this.PersonDevice = PersonDevice;
        this.RecordID = RecordID;
    }

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
    public void setPersonDepressionScore(Integer PersonDepressionScore){
        this.PersonDepressionScore = PersonDepressionScore;
    }

    public String getPersonDevice(){
        return PersonDevice;
    }
    public void setPersonDevice(String PersonDevice){
        this.PersonDevice = PersonDevice;
    }

    public Integer getRecordId(){
        return RecordID;
    }
}
