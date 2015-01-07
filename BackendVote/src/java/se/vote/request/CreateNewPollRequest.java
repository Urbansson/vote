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
public class CreateNewPollRequest {

    private String uId;
    private String token;
    private String title;
    private String info;

    public CreateNewPollRequest(String uId, String token, String title, String info) {
        this.uId = uId;
        this.token = token;
        this.title = title;
        this.info = info;

    }

    public CreateNewPollRequest(String json) {
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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {

        Gson json = new Gson();
        return json.toJson(this, CreateNewPollRequest.class);

    }

    public String toJson() {

        return toString();
    }

    private void fromJson(String json) {

        Gson gson = new Gson();
        CreateNewPollRequest tmp = gson.fromJson(json, CreateNewPollRequest.class);
        this.uId = tmp.getuId();
        this.token = tmp.getToken();
        this.title = tmp.getTitle();
        this.info = tmp.getInfo();
    }
}
