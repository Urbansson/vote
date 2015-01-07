/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vote.data;

import com.google.gson.Gson;
import com.restfb.types.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Teddy
 */
public class UserData {

    private String uId;
    private String info;

    private String firstName;
    private String lastName;
    private String pictureUrl;

    private UserData(){};
    
    /**
     * @return the uId
     */
    public String getuId() {
        return uId;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return the pictureUrl
     */
    public String getPictureUrl() {
        return pictureUrl;
    }

    /**
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {

        Gson gson = new Gson();
        return gson.toJson(this, UserData.class);
    }

    public String toJson() {
        return this.toString();
    }

    public static UserData fromJson(String json) {
        Gson gson = new Gson();
        UserData tmp = gson.fromJson(json, UserData.class);

        return tmp;
    }
}
