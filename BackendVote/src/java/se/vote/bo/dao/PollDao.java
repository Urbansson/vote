/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vote.bo.dao;

import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.User;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import static se.vote.bo.dao.DataAccessObject.getEntityManager;
import static se.vote.bo.dao.DataAccessObject.getFaceBookClient;
import se.vote.bo.persist.MemberAnswered;
import se.vote.bo.persist.MemberAnsweredPK;

import se.vote.bo.persist.TChoice;
import se.vote.bo.persist.TPoll;
import se.vote.bo.persist.TQuestion;
import se.vote.bo.persist.TUser;

/**
 *
 * @author Teddy
 */
    //!createnewpoll !addquestion !getPollById !getPollByOwner !getPollByMember !getQuestinById !answerQuestion
public class PollDao extends DataAccessObject {

    public static boolean createnewpoll(String ownerId, String accessToken, String pollTitle, String info) {

        System.out.println("Trying to add poll");

        boolean returnvalue = false;
        EntityManager em = getEntityManager();

        FacebookClient facebookClient = getFaceBookClient(accessToken);
        User FBuser = facebookClient.fetchObject("me", User.class, Parameter.with("fields", "id,email,first_name,last_name,picture.type(large)"));

        //Not correct token for this user;
        if (!ownerId.equalsIgnoreCase(FBuser.getId())) {
            return returnvalue;
        }

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            TUser dbUser = em.find(TUser.class, ownerId);

            TPoll persistPoll = new TPoll();
            persistPoll.setPollOwner(dbUser);
            persistPoll.setInfo(info);
            persistPoll.setTitle(pollTitle);

            em.persist(persistPoll);
            em.flush();

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

    public static boolean addquestionToPoll(String ownerId, String accessToken, int pollId, String questionTitle, String Question, List<String> choises) {

        System.out.println("Trying to add question ");

        boolean returnvalue = false;
        EntityManager em = getEntityManager();

        FacebookClient facebookClient = getFaceBookClient(accessToken);
        User FBuser = facebookClient.fetchObject("me", User.class, Parameter.with("fields", "id,email,first_name,last_name,picture.type(large)"));

        //Not correct token for this user;
        if (!ownerId.equalsIgnoreCase(FBuser.getId())) {
            return returnvalue;
        }

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TQuestion persistQuestion = new TQuestion();

            TUser dbUser = em.find(TUser.class, ownerId);

            //Might need to convert to int
            TPoll dbPoll = em.find(TPoll.class, pollId);
            int nrOfQuestions = dbPoll.getTQuestionCollection().size();

            persistQuestion.setPollId(dbPoll);
            persistQuestion.setQuestionNr(nrOfQuestions);
            persistQuestion.setQuestion(Question);

            persistQuestion.setQuestionTitle(questionTitle);

            List<TChoice> persistChoices = new ArrayList<>();
            int i = 0;
            for (String choise : choises) {
                TChoice persistChoice = new TChoice();
                persistChoice.setChoice(choise);
                persistChoice.setChoiceNr(i);
                persistChoice.setQuestionId(persistQuestion);
                em.persist(persistChoice);
                i++;
            }
            persistQuestion.setTChoiceCollection(persistChoices);
            dbPoll.getTQuestionCollection().add(persistQuestion);

            em.persist(persistQuestion);
            em.persist(dbPoll);
            em.flush();

            tx.commit();

            returnvalue = true;
        } catch (RollbackException rbe) {
            System.out.println("Rollback Error: " + rbe);
        } catch (Exception e) {
            System.out.println("Other Error: " + e);
        } finally {
            em.clear();

            em.close();
        }
        return returnvalue;

    }

    public static boolean answerQuestion(String user, String accessToken, int questionId, int choiceId) {

        System.out.println("Trying to answer Question ");

        boolean returnvalue = false;

        EntityManager em = getEntityManager();

        FacebookClient facebookClient = getFaceBookClient(accessToken);
        User FBuser = facebookClient.fetchObject("me", User.class, Parameter.with("fields", "id,email,first_name,last_name,picture.type(large)"));

        //Not correct token for this user;
        if (!user.equalsIgnoreCase(FBuser.getId())) {
            return false;
        }

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            TQuestion dbQuestion = em.find(TQuestion.class, questionId);

            //Owner cant vote on own  poll
            if (dbQuestion.getPollId().getPollOwner().getFbId().equalsIgnoreCase(user)) {
                return returnvalue;
            }

            //Check if the voter is a member of the poll
            //Setting all the choices need named query to get them in the right order
            Query q = em.createNamedQuery("TChoice.findbyQuestionChoice", TUser.class);
            TChoice dbChoice = (TChoice) q.setParameter("questionId", dbQuestion).setParameter("choiceNr", choiceId).getSingleResult();

            //Should fix database so i dont need this
            if (dbChoice.getNrOfChoices() == null) {
                dbChoice.setNrOfChoices(1);
            } else {
                dbChoice.setNrOfChoices(dbChoice.getNrOfChoices() + 1);
            }
            System.out.println("Choice is:" + dbChoice.getChoice());

            //MemberAnsweredPK key = new MemberAnsweredPK(user, questionId);
            MemberAnswered answ = new MemberAnswered(user, questionId);

            answ.setAnswered(true);

            em.persist(answ);

            em.flush();
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

    public static boolean addMembertoPoll(String user, String accessToken, int pollId) {

        System.out.println("Trying to join ");

        boolean returnvalue = false;
        EntityManager em = getEntityManager();

        FacebookClient facebookClient = getFaceBookClient(accessToken);
        User FBuser = facebookClient.fetchObject("me", User.class, Parameter.with("fields", "id,email,first_name,last_name,picture.type(large)"));

        //Not correct token for this user;
        if (!user.equalsIgnoreCase(FBuser.getId())) {
            return returnvalue;
        }

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            TUser dbUser = em.find(TUser.class, user);

            TPoll dbPoll = em.find(TPoll.class, pollId);

            //Owner cant join his own poll
            if (dbPoll.getPollOwner().getFbId().equalsIgnoreCase(user)) {
                System.out.println("Owner trying to join");
                return returnvalue;
            }
            dbUser.getTPollCollection().add(dbPoll);
            dbPoll.getTUserCollection().add(dbUser);

            em.persist(dbPoll);
            em.persist(dbUser);

            tx.commit();
            returnvalue = true;
        } catch (RollbackException rbe) {
            System.out.println("Rollback Error: " + rbe);
        } catch (Exception e) {
            System.out.println("Other Error: " + e);
        } finally {
            em.clear();

            em.close();
        }
        return returnvalue;

    }

    public static boolean removeMemberfromPoll(String user, String accessToken, int pollId) {

        System.out.println("Trying to leave ");

        boolean returnvalue = false;
        EntityManager em = getEntityManager();

        FacebookClient facebookClient = getFaceBookClient(accessToken);
        User FBuser = facebookClient.fetchObject("me", User.class, Parameter.with("fields", "id,email,first_name,last_name,picture.type(large)"));

        //Not correct token for this user;
        if (!user.equalsIgnoreCase(FBuser.getId())) {
            return false;
        }

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            TUser dbUser = em.find(TUser.class, user);
            TPoll dbPoll = em.find(TPoll.class, pollId);

            dbUser.getTPollCollection().remove(dbPoll);
            dbPoll.getTUserCollection().remove(dbUser);

            em.persist(dbPoll);
            em.persist(dbUser);

            tx.commit();

            returnvalue = true;
        } catch (RollbackException rbe) {
            System.out.println("Rollback Error: " + rbe);
        } catch (Exception e) {
            System.out.println("Other Error: " + e);
        } finally {
            em.clear();
            em.close();
        }
        return returnvalue;
    }

    /*
     This one is used!    
     */
    public static PollData getUserPolls(String uId) {
        System.out.println("Trying to fetch polls: " + uId);

        PollData data = new PollData();

        EntityManager em = getEntityManager();
        em.clear();

        FacebookClient fbc = getFaceBookClient();

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            TUser dbUser = em.find(TUser.class, uId);

            System.out.println(dbUser.getTPollCollection1());
            System.out.println(dbUser.getTPollCollection());

            for (TPoll poll : dbUser.getTPollCollection1()) {
                User FBuser = fbc.fetchObject(poll.getPollOwner().getFbId(), User.class, Parameter.with("fields", "first_name,last_name"));
                data.addOwned(poll.getTitle(), FBuser.getFirstName() + " " + FBuser.getLastName(), "" + poll.getPollId());

            }
            for (TPoll poll : dbUser.getTPollCollection()) {
                User FBuser = fbc.fetchObject(poll.getPollOwner().getFbId(), User.class, Parameter.with("fields", "first_name,last_name"));
                data.addMember(poll.getTitle(), FBuser.getFirstName() + " " + FBuser.getLastName(), "" + poll.getPollId());

            }

            tx.commit();
        } catch (RollbackException rbe) {
            System.out.println("Rollback Error: " + rbe);
        } catch (Exception e) {
            System.out.println("Other Error: " + e);
        } finally {
            em.close();
        }

        return data;
    }

    public static QuestionData getPollByIdWithQuestionNr(int pollId, int questionNr, String uID) {

        System.out.println("Trying to fetch " + pollId + " question NR: " + questionNr);

        QuestionData data = new QuestionData();

        boolean returnvalue = false;
        EntityManager em = getEntityManager();
        FacebookClient fbc = getFaceBookClient();

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            TPoll dbPoll = em.find(TPoll.class, pollId);

            //Setting the info about the poll
            data.setPollId("" + dbPoll.getPollId());
            data.setPollInfo(dbPoll.getInfo());
            data.setPollTitle(dbPoll.getTitle());

            //Setting the owner
            User FBuser = fbc.fetchObject(dbPoll.getPollOwner().getFbId(), User.class, Parameter.with("fields", "id,email,first_name,last_name,picture.type(square)"));
            data.setOwner(FBuser.getId(), FBuser.getFirstName() + " " + FBuser.getLastName(), FBuser.getPicture().getUrl());

            Query q = em.createNamedQuery("TQuestion.findByQuestionNr", TUser.class);

            try {

                TQuestion question = (TQuestion) q.setParameter("questionNr", questionNr).setMaxResults(1).getSingleResult();
                System.out.println("Question NR " + question.getQuestionNr());

                //Setting data about the question
                data.setQuestionId("" + question.getQuestionId());
                data.setQuestion(question.getQuestion());
                data.setQuestionTitle(question.getQuestionTitle());

                if (question.getQuestionNr() == 0) {
                    data.setPrevQId("");
                } else {
                    data.setPrevQId("" + (question.getQuestionNr() - 1));
                }

                try {
                    Query queryFindNewestPoll = em.createNamedQuery("TQuestion.findNewestQuestionInPoll", TUser.class);
                    TQuestion questiontemp = (TQuestion) queryFindNewestPoll.setParameter("pollId", dbPoll).setMaxResults(1).getSingleResult();
                    //System.out.println("Question NR " + );

                    if (questiontemp.getQuestionNr() == questionNr) {
                        data.setNextQId("");
                    } else {
                        data.setNextQId("" + (questionNr + 1));
                    }
                } catch (Exception e) {
                    data.setNextQId("");
                }

                //Setting all the choices need named query to get them in the right order
                q = em.createNamedQuery("TChoice.findbyQuestion", TUser.class);
                List<TChoice> choices = (List<TChoice>) q.setParameter("questionId", question).getResultList();
                for (TChoice choice : choices) {
                    data.addChoices(choice.getChoice());
                    data.addGraphData(choice.getChoice(), "" + choice.getNrOfChoices());
                }

                //Native query Need to make pritty but this works but is hard to read...
                String queryStr = "SELECT * FROM POLL_MEMBERS_ANSWERED WHERE POLL_ID = ? AND QUESTION_NR = ?";
                Query query = em.createNativeQuery(queryStr);
                query.setParameter(1, dbPoll.getPollId());
                query.setParameter(2, question.getQuestionNr());

                List<Object[]> dataas = query.getResultList();

                for (Object[] dataa : dataas) {

                    System.out.println("Data: " + dataa[0]);//FB_ID
                    System.out.println("Data: " + dataa[4]);//FB_ID

                    FBuser = fbc.fetchObject(dataa[0].toString(), User.class, Parameter.with("fields", "id,email,first_name,last_name,picture.type(square)"));

                    boolean answered = false;

                    if (!(dataa[4] == null)) {
                        System.out.println("Not null test");
                        answered = (boolean) dataa[4];
                    }

                    if (FBuser.getId().equalsIgnoreCase(uID)) {
                        data.setAnswered(answered);
                    } else {
                        data.setAnswered(false);
                    }

                    data.addQuestionMember(data.new Member(FBuser.getId(), FBuser.getFirstName() + " " + FBuser.getLastName(), FBuser.getPicture().getUrl(), answered));
                }

            } catch (Exception e) {

                data.setQuestionId("");
                data.setQuestion("");
                data.setQuestionTitle("");

                data.setPrevQId("");
                data.setNextQId("");

                for (TUser user : dbPoll.getTUserCollection()) {
                    FBuser = fbc.fetchObject(user.getFbId(), User.class, Parameter.with("fields", "id,email,first_name,last_name,picture.type(square)"));
                    data.addQuestionMember(data.new Member(FBuser.getId(), FBuser.getFirstName() + " " + FBuser.getLastName(), FBuser.getPicture().getUrl(), false));
                }

            }

            em.flush();
            tx.commit();

            returnvalue = true;
        } catch (RollbackException rbe) {
            System.out.println("Rollback Error: " + rbe);
        } catch (Exception e) {
            System.out.println("Other Error: " + e);
        } finally {
            em.close();
        }

        return data;
    }

    public static QuestionData getPollById(int pollId, String uID) {
        System.out.println("Trying to fetch " + pollId);

        QuestionData data = new QuestionData();

        boolean returnvalue = false;
        EntityManager em = getEntityManager();
        FacebookClient fbc = getFaceBookClient();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            TPoll dbPoll = em.find(TPoll.class, pollId);

            //Setting the info about the poll
            data.setPollId("" + dbPoll.getPollId());
            data.setPollInfo(dbPoll.getInfo());
            data.setPollTitle(dbPoll.getTitle());

            //Setting the owner
            User FBuser = fbc.fetchObject(dbPoll.getPollOwner().getFbId(), User.class, Parameter.with("fields", "id,email,first_name,last_name,picture.type(square)"));
            data.setOwner(FBuser.getId(), FBuser.getFirstName() + " " + FBuser.getLastName(), FBuser.getPicture().getUrl());

            Query q = em.createNamedQuery("TQuestion.findNewestQuestionInPoll", TUser.class);

            try {

                TQuestion question = (TQuestion) q.setParameter("pollId", dbPoll).setMaxResults(1).getSingleResult();
                System.out.println("Question NR " + question.getQuestionNr());

                //Setting data about the question
                data.setQuestionId("" + question.getQuestionId());
                data.setQuestion(question.getQuestion());
                data.setQuestionTitle(question.getQuestionTitle());

                if (question.getQuestionNr() == 0) {
                    data.setPrevQId("");
                } else {
                    data.setPrevQId("" + (question.getQuestionNr() - 1));
                }

                data.setNextQId("");

                //Setting all the choices need named query to get them in the right order
                q = em.createNamedQuery("TChoice.findbyQuestion", TUser.class);
                List<TChoice> choices = (List<TChoice>) q.setParameter("questionId", question).getResultList();
                for (TChoice choice : choices) {
                    data.addChoices(choice.getChoice());
                    data.addGraphData(choice.getChoice(), "" + choice.getNrOfChoices());
                }

                //Native query Need to make pritty but this works but is hard to read...
                String queryStr = "SELECT * FROM POLL_MEMBERS_ANSWERED WHERE POLL_ID = ? AND QUESTION_NR = ?";
                Query query = em.createNativeQuery(queryStr);
                query.setParameter(1, dbPoll.getPollId());
                query.setParameter(2, question.getQuestionNr());

                List<Object[]> dataas = query.getResultList();

                data.setAnswered(false);
                for (Object[] dataa : dataas) {

                    System.out.println("Data: " + dataa[0]);//FB_ID
                    System.out.println("Data: " + dataa[4]);//FB_ID

                    FBuser = fbc.fetchObject(dataa[0].toString(), User.class, Parameter.with("fields", "id,email,first_name,last_name,picture.type(square)"));

                    boolean answered = false;

                    if (!(dataa[4] == null)) {
                        System.out.println("Not null test");
                        answered = (boolean) dataa[4];
                    }

                    if (FBuser.getId().equalsIgnoreCase(uID)) {
                        data.setAnswered(answered);
                        System.out.println("This is user: "+answered);
                    }

                    data.addQuestionMember(data.new Member(FBuser.getId(), FBuser.getFirstName() + " " + FBuser.getLastName(), FBuser.getPicture().getUrl(), answered));
                }

            } catch (Exception e) {
                System.out.println("Okay dident find question no one here yet ruturn what we can");
                System.out.println("Error:" + e);

                data.setQuestionId("");
                data.setQuestion("");
                data.setQuestionTitle("");

                data.setPrevQId("");
                data.setNextQId("");

                for (TUser user : dbPoll.getTUserCollection()) {
                    FBuser = fbc.fetchObject(user.getFbId(), User.class, Parameter.with("fields", "id,email,first_name,last_name,picture.type(square)"));
                    data.addQuestionMember(data.new Member(FBuser.getId(), FBuser.getFirstName() + " " + FBuser.getLastName(), FBuser.getPicture().getUrl(), false));
                }

            }

            em.flush();
            tx.commit();

            returnvalue = true;
        } catch (RollbackException rbe) {
            System.out.println("Rollback Error: " + rbe);
        } catch (Exception e) {
            System.out.println("Other Error: " + e);
        } finally {
            em.close();
        }

        return data;
    }

    public static Set<String> getPollMembers(int pollId) {

        Set<String> members = new HashSet<>();
        System.out.println("Trying to fetch ");

        EntityManager em = getEntityManager();

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            TPoll poll = em.find(TPoll.class, pollId);

            for (TUser user : poll.getTUserCollection()) {
                members.add(user.getFbId());
            }

            tx.commit();
        } catch (RollbackException rbe) {
            System.out.println("Rollback Error: " + rbe);
        } catch (Exception e) {
            System.out.println("Other Error: " + e);
        } finally {
            em.close();
        }

        return members;
    }

    /*
     Never used can be removed?
    
    
    
     */
    public static void getPollByOwner(String uId) {
        System.out.println("Trying to fetch ");

        boolean returnvalue = false;
        EntityManager em = getEntityManager();

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            TUser dbUser = em.find(TUser.class, uId);

            for (TPoll poll : dbUser.getTPollCollection1()) {

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

    }

    //Dpligt namn hitta bättre
    public static void getPollByMember(String uId) {

        System.out.println("Trying to fetch ");

        boolean returnvalue = false;
        EntityManager em = getEntityManager();

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            TUser dbUser = em.find(TUser.class, uId);

            for (TPoll poll : dbUser.getTPollCollection()) {

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
    }

    public static void getQuestionById(String qId) {

        System.out.println("Trying to fetch ");

        boolean returnvalue = false;
        EntityManager em = getEntityManager();

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            TQuestion dbQuestion = em.find(TQuestion.class, qId);

            tx.commit();
            returnvalue = true;
        } catch (RollbackException rbe) {
            System.out.println("Rollback Error: " + rbe);
        } catch (Exception e) {
            System.out.println("Other Error: " + e);
        } finally {
            em.close();
        }

    }

}
