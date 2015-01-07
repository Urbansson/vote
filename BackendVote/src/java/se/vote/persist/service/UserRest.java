/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vote.persist.service;

import com.google.gson.Gson;
import java.util.ArrayList;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import se.vote.bo.dao.UserDao;
import se.vote.bo.dao.UserData;
import se.vote.request.AddDeviceRequest;
import se.vote.request.LoginRequest;
import se.vote.request.RemoveDeviceRequest;

/**
 * REST Web Service
 *
 * @author Teddy
 */
@Path("UserResources")
public class UserRest {

    @Context
    private UriInfo context;

    public UserRest() {
    }

    @POST
    @Path("/login")
    @Consumes("application/json")
    @Produces("application/json")
    public String login(String content) {

        LoginRequest request = new LoginRequest(content);

        UserData user = UserDao.login(request.getuId(), request.getToken());

        System.out.println(user.toJson());
        
        return user.toJson();
    }

    @POST
    @Path("/logout")
    @Consumes("application/json")
    @Produces("application/json")
    public String logout(String content) {

        //UserDoa.logout(content, content);
        return "{success: " + false + "}";
    }

    @PUT
    @Path("/addDevice")
    @Consumes("application/json")
    @Produces("application/json")
    public String addDevice(String content) {

        AddDeviceRequest request = new AddDeviceRequest(content);

        boolean success =  UserDao.addDevice(request.getuId(), request.getToken(), request.getType(), request.getDeviceToken());

        return "{success: " + success + "}";
    }

    @PUT
    @Path("/removeDevice")
    @Consumes("application/json")
    @Produces("application/json")
    public String removeDevice(String content) {

        RemoveDeviceRequest request = new RemoveDeviceRequest(content);

        boolean success = UserDao.removeDevice(request.getuId(), request.getToken(), request.getDeviceToken());
        return "{success: " + success + "}";
    }

    @GET
    @Path("/getUserById/{uId}")
    @Produces("application/json")
    public String getUserById(@PathParam("uId") String uId) {

        UserData user = UserDao.getUserById(uId);
        return user.toJson();
    }

}
