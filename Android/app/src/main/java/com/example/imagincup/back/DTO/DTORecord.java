package com.example.imagincup.back.DTO;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class DTORecord implements Serializable {

    private Integer RecordID;
    private Date RecordDate;// 날짜 DATETIME('NOW')
    private String Question; // 질문
    private String Answer; // 답변
    private Integer PersonID; // 유저 id
    private String Mission; // 미션 달성 여부, 기본값:0, 달성:1, 실패:2
    private String Emotion; // 감정분석 결과. 기본값:-, 긍정:, 부정:
    private Integer QuestionID; // 해당 질문이 몇번째 질문인지
    private Float EmotionScore;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd");
    private SimpleDateFormat dateFormatDay = new SimpleDateFormat("dd");

//    public DTORecord(Integer RecordID, Integer PersonID, String Question, Integer QuestionID, String Mission, String Answer, String Emotion){
//        this.RecordID = RecordID;
//        this.PersonID = PersonID;
//        this.Question = Question;
//        this.QuestionID = QuestionID;
//        this.Answer = Answer;
//        this.Mission = Mission;
//        this.Emotion = Emotion;
//    }

    public DTORecord(Integer PersonID, String Question, Integer QuestionID, String Mission, String Answer, String Emotion, Date RecordDate, Float EmotionScore){
        this.PersonID = PersonID;
        this.Question = Question;
        this.QuestionID = QuestionID;
        this.Answer = Answer;
        this.Mission = Mission;
        this.Emotion = Emotion;
        this.RecordDate = RecordDate;
        this.EmotionScore = EmotionScore;
    }

//    public static String getToDay(){
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        return sdf.format(new Date());
//    }

    public Integer getRecordID(){
        return RecordID;
    }
    public Integer getPersonID(){
        return PersonID;
    }

    public String getQuestion(){
        return Question;
    }
    public void setQuestion(String Question){
        this.Question = Question;
    }

    public Integer getQuestionID(){
        return QuestionID;
    }
    public void setQuestionID(Integer QuestionID){ this.QuestionID = QuestionID; }

    public String getAnswer(){
        return Answer;
    }
    public void setAnswer(String PersonDevice){
        this.Answer = Answer;
    }

    public String getMission(){
        return Mission;
    }
    public void setMission(String Mission){
        this.Mission = Mission;
    }

    public String getEmotion(){
        return Emotion;
    }
    public void setEmotion(String Emotion){
        this.Emotion = Emotion;
    }

    public Float getEmotionScore(){ return EmotionScore; }
    public void setEmotionScore(Float EmotionScore){
        this.EmotionScore = EmotionScore;
    }

    public String getRecordDate(){
        return dateFormatDay.format(RecordDate);
    }
    public String getRecordDay(){
        return dateFormat.format(RecordDate);
    }
    public void setRecordDate(Date RecordDate){
        this.RecordDate = RecordDate;
    }

}
