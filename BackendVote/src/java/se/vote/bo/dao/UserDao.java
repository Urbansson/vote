/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vote.bo.dao;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.User;
import java.util.Collection;
import java.util.Iterator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import se.vote.bo.persist.TDevice;
import se.vote.bo.persist.TUser;

/**
 *
 * @author Teddy
 */
//getUserById removeDevice addDevice logout login
public class UserDao extends DataAccessObject {

    public static UserData login(String uID, String accessToken) {

        UserData userData = null;

        EntityManager em = getEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            FacebookClient facebookClient = getFaceBookClient(accessToken);
            User FBuser = facebookClient.fetchObject("me", User.class, Parameter.with("fields", "id,email,first_name,last_name,picture.type(square)"));
            //Not correct user and token return false;
            if (!uID.equalsIgnoreCase(FBuser.getId())) {
                return null;
            }

            System.out.println("User name: " + FBuser.getFirstName());
            System.out.println("User name: " + FBuser.getLastName());

            System.out.println("User name: " + FBuser.getId());
            System.out.println("User name: " + FBuser.getEmail());
            System.out.println("User name: " + FBuser.getPicture().getUrl());

            Query q = em.createNamedQuery("TUser.findByFbId", TUser.class);
            if (q.setParameter("fbId", uID).getResultList().isEmpty()) {

                System.out.println("First Time");

                System.out.println("Id: " + uID + " Token: " + accessToken);

                TUser userPersist = new TUser();

                userPersist.setFbId(uID);
                userPersist.setFbToken(accessToken);

                em.persist(userPersist);
                em.flush();
                System.out.println("Created ");

            } else {
                System.out.println("User already Reged update token ");

                TUser userPersist = em.find(TUser.class, uID);

                userPersist.setFbToken(accessToken);
                em.persist(userPersist);
                em.flush();

            }
            TUser loginUser = em.find(TUser.class, uID);

            System.out.println("Return loginuser: " + loginUser.getFbId());

            userData = new UserData(loginUser);
            userData.setFbData(FBuser);

            tx.commit();

        } catch (RollbackException rbe) {
            System.out.println("Rollback Error: " + rbe);
        } catch (Exception e) {
            System.out.println("Other Error in Login: " + e);

        } finally {
            em.close();
        }

        return userData;
    }

    //Might not be needed

    public static void logout(String uID, String token) {

    }

    public static boolean addDevice(String uID, String accessToken, String type, String deviceToken) {

        System.out.println("Trying to add ");

        boolean returnvalue = false;
        EntityManager em = getEntityManager();

        FacebookClient facebookClient = getFaceBookClient(accessToken);
        User FBuser = facebookClient.fetchObject("me", User.class, Parameter.with("fields", "id,email,first_name,last_name,picture.type(large)"));

        //Not correct token for this user;
        if (!uID.equalsIgnoreCase(FBuser.getId())) {
            return false;
        }

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            TUser dbUser = em.find(TUser.class, uID);

            TDevice persistDevice = new TDevice();
            persistDevice.setDeviceToken(deviceToken);
            persistDevice.setDeviceOwner(dbUser);
            persistDevice.setDeviceInfo(type);

            em.persist(persistDevice);

            tx.commit();
            returnvalue = true;
        } catch (RollbackException rbe) {
            System.out.println("Rollback Error: " + rbe);
        } catch (Exception e) {
            System.out.println("Other Error: " + e);
        } finally {
            em.close();
        }

        return returnvalue;
    }

    public static boolean removeDevice(String uID, String accessToken, String deviceToken) {
        System.out.println("Trying to remove ");

        boolean returnvalue = false;
        EntityManager em = getEntityManager();

        FacebookClient facebookClient = getFaceBookClient(accessToken);
        User FBuser = facebookClient.fetchObject("me", User.class, Parameter.with("fields", "id,email,first_name,last_name,picture.type(large)"));

        //Not correct token for this user;
        if (!uID.equalsIgnoreCase(FBuser.getId())) {
            return false;
        }

        EntityTransaction tx = em.getTransaction();
        try {

            tx.begin();
            TUser dbUser = em.find(TUser.class, uID);
            Iterator<TDevice> i = dbUser.getTDeviceCollection().iterator();
            while (i.hasNext()) {
                TDevice device = i.next();

                if (device.getDeviceToken().equalsIgnoreCase(deviceToken)) {
                    System.out.println("Removing");
                    em.remove(device);
                    break;
                }
            }
            tx.commit();
            returnvalue = true;
        } catch (RollbackException rbe) {
            System.out.println("Rollback Error: " + rbe);
        } catch (Exception e) {
            System.out.println("Other Error: " + e);
        } finally {
            em.close();
        }
        return returnvalue;

    }

    public static UserData getUserById(String uID) {
        System.out.println("Trying to getch user by id ");

        UserData userData = null;

        boolean returnvalue = false;
        EntityManager em = getEntityManager();

        EntityTransaction tx = em.getTransaction();

        FacebookClient facebookClient = getFaceBookClient();
        User FBuser = facebookClient.fetchObject(uID, User.class, Parameter.with("fields", "id,email,first_name,last_name,picture.type(large)"));

        try {
            tx.begin();

            TUser dbUser = em.find(TUser.class, uID);

            userData = new UserData(dbUser);
            userData.setFbData(FBuser);
            tx.commit();
            returnvalue = true;
        } catch (RollbackException rbe) {
            System.out.println("Rollback Error: " + rbe);
        } catch (Exception e) {
            System.out.println("Other Error: " + e);
        } finally {
            em.close();
        }

        return userData;
    }
}
