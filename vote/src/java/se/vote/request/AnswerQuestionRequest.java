/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vote.request;

import com.google.gson.Gson;

/**
 *
 * @author Teddy
 */
public class AnswerQuestionRequest {

    private String uId;
    private String token;
    private int questionId;
    private int choiceId;

    public AnswerQuestionRequest(String uId, String token, int questionId, int choiceId) {

        this.uId = uId;
        this.token = token;
        this.questionId = questionId;
        this.choiceId = choiceId;

    }

    public AnswerQuestionRequest(String json) {

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
     * @return the questionId
     */
    public int getQuestionId() {
        return questionId;
    }

    /**
     * @return the choiceId
     */
    public int getChoiceId() {
        return choiceId;
    }

    @Override
    public String toString() {

        Gson json = new Gson();
        return json.toJson(this, AnswerQuestionRequest.class);

    }

    public String toJson() {

        return toString();
    }

    private void fromJson(String json) {

        Gson gson = new Gson();
        AnswerQuestionRequest tmp = gson.fromJson(json, AnswerQuestionRequest.class);

    }

}
