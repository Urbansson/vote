/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.social.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import se.vote.request.AddQuestionRequest;
import se.vote.rest.PollRestClient;

/**
 *
 * @author Teddy
 */
@ManagedBean
@RequestScoped
public class NewQuestionBean {

    @ManagedProperty(value = "#{userBean}")
    private userBean user;

    private String title;
    private String question;

    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;
    private String choice5;
    private String choice6;

    public void createQuestion(int pollId) {

        System.out.println(pollId);
        ArrayList<String> list = new ArrayList();

        if (!choice1.isEmpty()) {
            list.add(choice1);
        }
        if (!choice2.isEmpty()) {
            list.add(choice2);
        }
        if (!choice3.isEmpty()) {
            list.add(choice3);
        }
        if (!choice4.isEmpty()) {
            list.add(choice4);
        }
        if (!choice5.isEmpty()) {
            list.add(choice5);
        }
        if (!choice6.isEmpty()) {
            list.add(choice6);
        }

        if (!list.isEmpty() && !title.isEmpty() && !question.isEmpty()) {
            PollRestClient rest = new PollRestClient();

            AddQuestionRequest request = new AddQuestionRequest(user.getUID(), user.getaccessToken(), pollId, title, question, list);

            System.out.println(request.getQuestionTitle());
            System.out.println(request.getQuestion());

            for (String col : request.getChoices()) {
                System.out.println(col);

            }

            String response = rest.addquestion(request.toJson());

            System.out.println("Response:" + response);

            if (response.contains("true")) {
                System.out.println("Ghetto but works");
                try {
                    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
                } catch (IOException ex) {
                    Logger.getLogger(NewQuestionBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {
            System.out.println("Missing fields ajax back");
        }

    }

    public boolean isAnswered() {
        return false;
    }

    /**
     * Creates a new instance of CreatePollBean
     */
    public NewQuestionBean() {
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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question the question to set
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return the choice1
     */
    public String getChoice1() {
        return choice1;
    }

    /**
     * @param choice1 the choice1 to set
     */
    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    /**
     * @return the choice2
     */
    public String getChoice2() {
        return choice2;
    }

    /**
     * @param choice2 the choice2 to set
     */
    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    /**
     * @return the choice3
     */
    public String getChoice3() {
        return choice3;
    }

    /**
     * @param choice3 the choice3 to set
     */
    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    /**
     * @return the choice4
     */
    public String getChoice4() {
        return choice4;
    }

    /**
     * @param choice4 the choice4 to set
     */
    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }

    /**
     * @return the choice5
     */
    public String getChoice5() {
        return choice5;
    }

    /**
     * @param choice5 the choice5 to set
     */
    public void setChoice5(String choice5) {
        this.choice5 = choice5;
    }

    /**
     * @return the choice6
     */
    public String getChoice6() {
        return choice6;
    }

    /**
     * @param choice6 the choice6 to set
     */
    public void setChoice6(String choice6) {
        this.choice6 = choice6;
    }

}
