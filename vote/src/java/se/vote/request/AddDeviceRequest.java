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
public class AddDeviceRequest {

    private String uId;
    private String token;
    private String type;
    private String deviceToken;

    public AddDeviceRequest(String uId, String token, String type, String deviceToken) {

        this.uId = uId;
        this.token = token;
        this.type = type;
        this.deviceToken = deviceToken;

    }
    
    public AddDeviceRequest(String json){
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
     * @return the type
     */
    public String getType() {
        return type;
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
        return json.toJson(this, AddDeviceRequest.class);

    }

    public String toJson() {

        return toString();
    }

    private void fromJson(String json) {

        Gson gson = new Gson();
        AddDeviceRequest tmp = gson.fromJson(json, AddDeviceRequest.class);

        this.token = tmp.token;
        this.uId = tmp.uId;
        this.type = tmp.type;
        this.deviceToken = tmp.deviceToken;

    }

}
