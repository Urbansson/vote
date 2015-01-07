/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vote.persist.service;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import se.vote.bo.dao.PollDao;
import se.vote.bo.dao.PollData;
import se.vote.bo.dao.QuestionData;
import se.vote.request.AddQuestionRequest;
import se.vote.request.AnswerQuestionRequest;
import se.vote.request.CreateNewPollRequest;
import se.vote.request.JoinLeavePollRequest;
import se.vote.socket.AnswerQuestionSocket;
import se.vote.socket.InformationSocket;

/**
 * REST Web Service
 *
 * @author Teddy
 */
@Path("pollrest")
public class PollRest {

    @Context
    private UriInfo context;

    public PollRest() {
    }

    @PUT
    @Path("/createnewpoll")
    @Consumes("application/json")
    @Produces("application/json")
    public String createnewpoll(String content) {

        CreateNewPollRequest request = new CreateNewPollRequest(content);

        //Fix return value
        boolean success = PollDao.createnewpoll(request.getuId(), request.getToken(), request.getTitle(), request.getInfo());

        return "{\"success\": \"" + success + "\"}";
    }

    @PUT
    @Path("/addquestion")
    @Consumes("application/json")
    @Produces("application/json")
    public String addquestion(String content) {
        //Class with id token pollid question and choices

        AddQuestionRequest request = new AddQuestionRequest(content);

        System.out.println("-+-++-+" + request.getQuestionTitle());
        System.out.println(request.getQuestion());

        for (String col : request.getChoices()) {
            System.out.println(col);

        }

        boolean success = PollDao.addquestionToPoll(request.getuId(), request.getToken(), request.getPollId(), request.getQuestionTitle(), request.getQuestion(), request.getChoices());

        InformationSocket infoSocket = new InformationSocket();

        if (success) {
            try {
                infoSocket.broadcastPoll(request.getPollId());
            } catch (IOException ex) {
            }
        }
        return "{\"success\": \"" + success + "\"}";

    }

    @GET
    @Path("/getPollById/{pollId}/{uId}")
    @Produces("application/json")
    public String getPollById(@PathParam("pollId") int pollId, @PathParam("uId") String uId) {

        QuestionData data = PollDao.getPollById(pollId, uId);

        return data.toJSON();
    }

    @GET
    @Path("/getPollByIdWithQuestionNr/{pollId}/{questionNr}/{uId}")
    @Produces("application/json")
    public String getPollByIdWithQuestionNr(@PathParam("pollId") int pollId, @PathParam("questionNr") int questionNr, @PathParam("uId") String uId) {

        QuestionData data = PollDao.getPollByIdWithQuestionNr(pollId, questionNr, uId);

        return data.toJSON();
    }

    @GET
    @Path("/getUserPolls/{uId}")
    @Produces("application/json")
    public String getUserPolls(@PathParam("uId") String uId) {

        //PollDao.getPollByOwner(uId);
        PollData data = PollDao.getUserPolls(uId);

        return data.toJSON();
    }

    @GET
    @Path("/getPollByOwner/{uId}")
    @Produces("application/json")
    public String getPollByOwner(@PathParam("uId") String uId) {

        PollDao.getPollByOwner(uId);

        return "";
    }

    @GET
    @Path("/getPollByMember/{uId}")
    @Produces("application/json")
    public String getPollByMember(@PathParam("uId") String uId) {

        PollDao.getPollByMember(uId);

        return "";
    }

    @GET
    @Path("/getQuestinById/{questionId}")
    @Produces("application/json")
    public String getQuestinById(@PathParam("questionId") String questionId) {

        PollDao.getQuestionById(questionId);

        return "";
    }

    @POST
    @Path("/answerQuestion")
    @Consumes("application/json")
    @Produces("application/json")
    public String answerQuestion(String content) {
        //Class with user token question id and choice id/nr

        AnswerQuestionRequest request = new AnswerQuestionRequest(content);

        System.out.println(request.getQuestionId());
        System.out.println(request.getChoiceId());

        Boolean success = PollDao.answerQuestion(request.getuId(), request.getToken(), request.getQuestionId(), request.getChoiceId());

        AnswerQuestionSocket answerSocket = new AnswerQuestionSocket();

        if (success) {
            try {
                answerSocket.broadcastAnswer(request.getQuestionId(),request.getChoiceId(), request.getuId());
            } catch (IOException ex) {
            }
        }

        return "{\"success\": \"" + success + "\"}";
    }

    @POST
    @Path("/joinPoll")
    @Consumes("application/json")
    @Produces("application/json")
    public String joinPoll(String content) {
        //Class with user token question id and choice id/nr
        JoinLeavePollRequest request = new JoinLeavePollRequest(content);

        Boolean success = PollDao.addMembertoPoll(request.getuId(), request.getToken(), request.getPollId());
        return "{success: " + success + "}";
    }

    @POST
    @Path("/leavePoll")
    @Consumes("application/json")
    @Produces("application/json")
    public String leavePoll(String content) {
        //Class with user token question id and choice id/nr

        JoinLeavePollRequest request = new JoinLeavePollRequest(content);

        System.out.println("uid " + request.getuId());
        System.out.println("token " + request.getToken());
        System.out.println("pollId " + request.getPollId());

        Boolean success = PollDao.removeMemberfromPoll(request.getuId(), request.getToken(), request.getPollId());
        return "{success: " + success + "}";
    }

}
