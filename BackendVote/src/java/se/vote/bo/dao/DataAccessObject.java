/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vote.bo.dao;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Teddy
 */
public class DataAccessObject {

    private static final String APP_SECRET = "7568049d4ec1dac473e47229ae990139";
    private static final String APP_ID = "1501024336852791";

    public static EntityManager getEntityManager() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BackendPU");
        EntityManager em = emf.createEntityManager();

        return em;
    }

    public static FacebookClient getFaceBookClient(String token) {

        FacebookClient facebookClient = new DefaultFacebookClient(token, APP_SECRET);

        return facebookClient;
    }

    public static FacebookClient getFaceBookClient() {

        AccessToken accessToken = new DefaultFacebookClient().obtainAppAccessToken("1501024336852791", "7568049d4ec1dac473e47229ae990139");
        DefaultFacebookClient facebookClient = new DefaultFacebookClient(accessToken.getAccessToken());

        return facebookClient;
    }

}
