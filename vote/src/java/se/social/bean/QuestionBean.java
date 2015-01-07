/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import se.vote.data.QuestionData;
import se.vote.data.QuestionData.Member;
import se.vote.request.AnswerQuestionRequest;
import se.vote.rest.PollRestClient;

/**
 *
 * @author Teddy
 */
@ManagedBean
@SessionScoped
public class QuestionBean {

    @ManagedProperty(value = "#{userBean}")
    private userBean user;

    private QuestionData data = new QuestionData();
    ;

    private String pollId = "";

    /**
     * Creates a new instance of QuestionBean
     */
    public QuestionBean() {

        System.out.println("CREATING BEAN");

    }

    public String init() {
        if (pollId.isEmpty()) {
            System.out.println("Go back to find nothing here    ");
            return "home.xhtml";//Later a error page or something

        } else {
            System.out.println("Fetching data from backend");

            PollRestClient rest = new PollRestClient();
            String response = rest.getPollById(pollId, user.getUID());
            System.out.println("Response " + response);

            this.data = QuestionData.fromJSON(response);

            return "poll.xhtml?pollId=" + this.pollId;
        }
    }

    public void fetchNextQuestion() {

        System.out.println("Next is: " + this.data.getNextQId());

        if (!this.data.getNextQId().isEmpty()) {
            System.out.println("Fetching next");

            PollRestClient rest = new PollRestClient();

            String response = rest.getPollByIdWithQuestionNr(data.getPollId(), data.getNextQId(), user.getUID());
            System.out.println("response: " + response);
            this.data = QuestionData.fromJSON(response);

        } else {
            System.out.println("missing data");
        }

    }

    public void fetchPrevQuestion() {

        if (!this.data.getPrevQId().isEmpty()) {
            System.out.println("Fetching Prev");

            PollRestClient rest = new PollRestClient();

            String response = rest.getPollByIdWithQuestionNr(data.getPollId(), data.getPrevQId(), user.getUID());

            System.out.println("response: " + response);
            this.data = QuestionData.fromJSON(response);

        } else {
            System.out.println("missing data");
        }

    }

    public void answerQuestion(String id) {

        System.out.println("Send: " + id);

        PollRestClient rest = new PollRestClient();

        AnswerQuestionRequest request = new AnswerQuestionRequest(user.getUID(), user.getaccessToken(), Integer.valueOf(data.getQuestionId()), Integer.valueOf(id));

        String response = rest.answerQuestion(request.toJson());
        System.out.println("Response: " + response);

        if (response.contains("true")) {
            System.out.println("Updating Question");

            String currentQuestionId;
            try {
                currentQuestionId = "" + (Integer.valueOf(data.getPrevQId()) + 1);
            } catch (Exception e) {
                currentQuestionId = "0";
            }

            response = rest.getPollByIdWithQuestionNr(data.getPollId(), currentQuestionId, user.getUID());

            System.out.println("response: " + response);
            this.data = QuestionData.fromJSON(response);

            //this.init();
        }
    }

    public void skipQuestion() {
        System.out.println("Skiping");
    }

    public boolean isOwner() {
        return user.getUID().equalsIgnoreCase(data.getOwner().getId());
    }

    public boolean isPressent() {
        return !data.getQuestionId().isEmpty();
    }

    public QuestionData getData() {

        return data;

    }

    public boolean isAnswered() {
        return data.isAnswered();
    }

    /**
     * @return the user
     */
    public userBean getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(userBean user) {
        this.user = user;
    }

    /**
     * @return the questionId
     */
    public String getPollId() {
        return pollId;
    }

    /**
     * @param pollId
     */
    public void setPollId(String pollId) {
        this.pollId = pollId;
    }

}
