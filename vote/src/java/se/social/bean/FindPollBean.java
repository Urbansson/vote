/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.bean;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import se.vote.data.PollData;
import se.vote.request.CreateNewPollRequest;
import se.vote.request.JoinLeavePollRequest;
import se.vote.rest.PollRestClient;

/**
 *
 * @author Teddy
 */
@ManagedBean
@SessionScoped
public class FindPollBean {

    @ManagedProperty(value = "#{userBean}")
    private userBean user;

    private PollData data;

    private String joinPollId;

    private String newPollName;
    private String newPollInfo;

    public FindPollBean() {
        data = new PollData();
    }

    public void init() {

        PollRestClient rest = new PollRestClient();
        String response = rest.getUserPolls(user.getUID());

        System.out.println("Response: " + response);

        this.data = PollData.fromJSON(response);

    }

    public void joinPoll() throws IOException {

        System.out.println("Joining: " + getJoinPollId());

        if (!joinPollId.isEmpty()) {

            PollRestClient rest = new PollRestClient();

            String success = rest.joinPoll(new JoinLeavePollRequest(user.getUID(), user.getaccessToken(), Integer.valueOf(this.getJoinPollId())).toJson());

            if (success.contains("true")) {
                System.out.println("Success joining do something");
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.redirect(context.getRequestContextPath() + "/poll.xhtml?pollId=" + this.getJoinPollId());

            } else {
                System.out.println("failed joining do something else");

            }
        } else {
            System.out.println("Empty fields send do something else");
        }

    }

    public void leavePoll(String id) {

        System.out.println("LEaving: " + id);
        if (!id.isEmpty()) {

            PollRestClient rest = new PollRestClient();

            String success = rest.leavePoll(new JoinLeavePollRequest(user.getUID(), user.getaccessToken(), Integer.valueOf(id)).toJson());

            if (success.contains("true")) {
                System.out.println("Success leave do something");

                this.init();

            } else {
                System.out.println("failed joining do something else");

            }
        } else {
            System.out.println("Empty fields send do something else");
        }

    }

    public void createNewPoll() throws IOException {
        System.out.println("Creating: " + getNewPollName());
        System.out.println("info: " + getNewPollInfo());

        if (!(newPollName.isEmpty() && newPollInfo.isEmpty())) {
            PollRestClient rest = new PollRestClient();

            String success = rest.createnewpoll(new CreateNewPollRequest(user.getUID(), user.getaccessToken(), getNewPollName(), getNewPollInfo()).toJson());

            if (success.contains("true")) {
                System.out.println("Success send do something");
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.redirect(context.getRequestContextPath() + "/find.xhtml");
            } else {
                System.out.println("fail send do something else");

            }
        } else {
            System.out.println("Empty fields send do something else");
        }
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
     * @return the data
     */
    public PollData getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(PollData data) {
        this.data = data;
    }

    /**
     * @return the joinPollId
     */
    public String getJoinPollId() {
        return joinPollId;
    }

    /**
     * @param joinPollId the joinPollId to set
     */
    public void setJoinPollId(String joinPollId) {
        this.joinPollId = joinPollId;
    }

    /**
     * @return the newPollName
     */
    public String getNewPollName() {
        return newPollName;
    }

    /**
     * @param newPollName the newPollName to set
     */
    public void setNewPollName(String newPollName) {
        this.newPollName = newPollName;
    }

    /**
     * @return the newPollInfo
     */
    public String getNewPollInfo() {
        return newPollInfo;
    }

    /**
     * @param newPollInfo the newPollInfo to set
     */
    public void setNewPollInfo(String newPollInfo) {
        this.newPollInfo = newPollInfo;
    }

}
