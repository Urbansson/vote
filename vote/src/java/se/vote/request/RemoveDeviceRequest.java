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
public class RemoveDeviceRequest {

    private String uId;
    private String token;
    private String deviceToken;

    public RemoveDeviceRequest(String uId, String token, String deviceToken) {

        this.uId = uId;
        this.token = token;
        this.deviceToken = deviceToken;

    }
    
        public RemoveDeviceRequest(String json) {

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
     * @return the deviceToken
     */
    public String getDeviceToken() {
        return deviceToken;
    }

    @Override
    public String toString() {

        Gson json = new Gson();
        return json.toJson(this, RemoveDeviceRequest.class);

    }

    public String toJson() {

        return toString();
    }

    private void fromJson(String json) {

        Gson gson = new Gson();
        RemoveDeviceRequest tmp = gson.fromJson(json, RemoveDeviceRequest.class);

        this.token = tmp.token;
        this.uId = tmp.uId;
        this.deviceToken = tmp.deviceToken;

    }

}
