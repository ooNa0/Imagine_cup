package com.example.imagincup.back.DTO;

public class DTORecord {

    private Integer RecordID;
    //private m// 날짜 DATETIME('NOW')
    private String Question; // 질문
    private String Answer; // 답변
    private Integer PersonID; // 유저 id
    private String Mission; // 미션 달성 여부, 기본값:0, 달성:1, 실패:2
    private String Emotion; // 감정분석 결과. 기본값:-, 긍정:, 부정:
    private Integer QuestionID; // 해당 질문이 몇번째 질문인지

    public DTORecord(Integer RecordID, Integer PersonID, String Question, Integer QuestionID, String Mission, String Answer, String Emotion){
        this.RecordID = RecordID;
        this.PersonID = PersonID;
        this.Question = Question;
        this.QuestionID = QuestionID;
        this.Answer = Answer;
        this.Mission = Mission;
        this.Emotion = Emotion;
    }

//    public static String getToDay(){
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //시간 정보가 필요한 경우
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

}
