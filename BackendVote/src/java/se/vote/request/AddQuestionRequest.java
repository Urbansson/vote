/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vote.request;

import com.google.gson.Gson;
import java.util.List;

/**
 *
 * @author Teddy
 */
public class AddQuestionRequest {

    private String uId;
    private String token;
    private String questionTitle;
    private String question;
    private int pollId;
    private List<String> choices;

    public AddQuestionRequest(String uId, String token, int pollId, String questionTitle,  String question, List<String> choices) {

        this.uId = uId;
        this.token = token;
        this.question = question;
        this.questionTitle = questionTitle;
        this.pollId = pollId;
        this.choices = choices;

    }

    public AddQuestionRequest(String json) {
        this.fromJson(json);
    }

    /**
     * @return the uId
     */
    public String getuId() {
        return uId;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @return the pollId
     */
    public int getPollId() {
        return pollId;
    }

    /**
     * @return the choices
     */
    public List<String> getChoices() {
        return choices;
    }
    /**
     * @return the questionTitle
     */
    public String getQuestionTitle() {
        return questionTitle;
    }
    
    
    @Override
    public String toString() {

        Gson json = new Gson();
        return json.toJson(this, AddQuestionRequest.class);

    }

    public String toJson() {

        return toString();
    }

    private void fromJson(String json) {

        Gson gson = new Gson();
        AddQuestionRequest tmp = gson.fromJson(json, AddQuestionRequest.class);

        this.uId = tmp.getuId();
        this.token = tmp.getToken();
        this.question = tmp.getQuestion();
        this.pollId = tmp.getPollId();
        this.choices = tmp.getChoices();
        this.questionTitle = tmp.getQuestionTitle();
    }

}
