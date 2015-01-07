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
public class JoinLeavePollRequest {

    private String uId;
    private String token;
    private int pollId;

    public JoinLeavePollRequest(String uId, String token, int pollId) {
        this.uId = uId;
        this.token = token;
        this.pollId = pollId;
    } 

    public JoinLeavePollRequest(String json) {
        System.out.println("Hello from inside");
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
     * @return the pollId
     */
    public int getPollId() {
        return pollId;
    }

    @Override
    public String toString() {

        Gson json = new Gson();
        return json.toJson(this, JoinLeavePollRequest.class);

    }

    public String toJson() {

        return toString();
    }

    private void fromJson(String json) {
        Gson gson = new Gson();

        System.out.println("Before");

        JoinLeavePollRequest tmp = gson.fromJson(json, JoinLeavePollRequest.class);

        this.uId = tmp.getuId();
        this.token = tmp.getToken();
        this.pollId = tmp.getPollId();

    }
}
