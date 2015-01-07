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
public class LoginRequest {

    private String uId;
    private String token;

    public LoginRequest(String uId, String token) {

        this.uId = uId;
        this.token = token;
    }

    public LoginRequest(String json) {

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

    @Override
    public String toString() {

        Gson json = new Gson();
        return json.toJson(this, LoginRequest.class);

    }

    public String toJson() {

        return toString();
    }

    private void fromJson(String json) {

        Gson gson = new Gson();
        LoginRequest tmp = gson.fromJson(json, LoginRequest.class);

        this.token = tmp.token;
        this.uId = tmp.uId;

    }
}
