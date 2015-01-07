/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.bean;

import com.google.gson.Gson;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import se.vote.data.UserData;
import se.vote.request.LoginRequest;
import se.vote.rest.UserRestClient;

/**
 *
 * @author Teddy
 */
@ManagedBean
@SessionScoped
public class userBean {

    private static final String MY_APP_SECRET = "7568049d4ec1dac473e47229ae990139";
    private String accessToken;
    private String UID;

    private UserData data;

    private Boolean loggedin;

    /**
     * Creates a new instance of userBean
     */
    public userBean() {
        System.out.println("Made a user bean");
        this.accessToken = "";
        this.loggedin = false;

    }


    public void login() throws IOException {

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        String token = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("accessToken");
        String userID = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userID");

        //making the request
        LoginRequest request = new LoginRequest(userID, token);

        //sending request
        
        UserRestClient rest = new UserRestClient();
        String result = rest.login(request.toJson());

        //parsing from json to class
        UserData user = UserData.fromJson(result);

        if (user.getuId().equalsIgnoreCase(userID)) {
            this.loggedin = true;
            this.UID = userID;
            this.accessToken = token;
            this.data = user;

            System.out.println(UID);
            System.out.println(accessToken);

            context.redirect("home.xhtml");
        }

        System.out.println("Are you loggeded in: " + this.loggedin);

    }

    public boolean isLoggedIn() {

        return loggedin;
    }

    public void logout() throws IOException {

        System.out.println("Logging out: ");
        
         this.loggedin = false;
         FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
         FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

         this.accessToken = "";
         this.UID = "";
         this.data = null;
         
    }

    /**
     * @return the accessToken
     */
    public String getaccessToken() {
        return accessToken;
    }

    public String getUID() {
        return UID;
    }

    /**
     * @return the logedin
     */
    public Boolean getLoggedin() {
        return loggedin;
    }

    public UserData getUserData() {
        return data;
    }
}
