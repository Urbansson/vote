/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vote.bo.dao;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Teddy
 */
public class QuestionData {

    private String pollTitle;
    private String pollInfo;
    private String pollId;
    private Member owner;

    private GraphData GraphData;

    private String questionId;
    private String questionTitle;
    private String question;
    private List<String> choices;
    private List<Member> questionMembers;

    private boolean answered;

    private String nextQId;
    private String prevQId;

    public QuestionData() {
        choices = new ArrayList<>();
        questionMembers = new ArrayList<>();
        GraphData = new GraphData();
    }

    /**
     * @return the pollTitle
     */
    public String getPollTitle() {
        return pollTitle;
    }

    /**
     * @param pollTitle the pollTitle to set
     */
    public void setPollTitle(String pollTitle) {
        this.pollTitle = pollTitle;
    }

    /**
     * @return the pollInfo
     */
    public String getPollInfo() {
        return pollInfo;
    }

    /**
     * @param pollInfo the pollInfo to set
     */
    public void setPollInfo(String pollInfo) {
        this.pollInfo = pollInfo;
    }

    /**
     * @return the pollId
     */
    public String getPollId() {
        return pollId;
    }

    /**
     * @param pollId the pollId to set
     */
    public void setPollId(String pollId) {
        this.pollId = pollId;
    }

    /**
     * @return the owner
     */
    public Member getOwner() {
        return owner;
    }

    /**
     * @param id
     * @param name
     * @param pictureUrl
     */
    public void setOwner(String id, String name, String pictureUrl) {

        Member tmp = new Member(id, name, pictureUrl, false);
        this.owner = tmp;
    }

    /**
     * @return the chartJsonData
     */
    public String getGraphData() {
        return GraphData.toJSON();
    }

    /**
     * @param choice
     * @param NrOfChoices
     */
    public void addGraphData(String choice, String NrOfChoices) {
        this.GraphData.addData(choice, NrOfChoices);
    }

    /**
     * @return the questionTitle
     */
    public String getQuestionTitle() {
        return questionTitle;
    }

    /**
     * @param questionTitle the questionTitle to set
     */
    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
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
     * @return the choices
     */
    public List<String> getChoices() {
        return choices;
    }

    /**
     * @param choice
     */
    public void addChoices(String choice) {
        this.choices.add(choice);
    }

    public void removeChoices(String choice) {
        this.choices.remove(choice);
    }

    public void removeAllChoices() {
        this.choices = new ArrayList<>();

    }

    /**
     * @return the questionMembers
     */
    public List<Member> getQuestionMembers() {
        return questionMembers;
    }

    public void addQuestionMember(Member member) {
        this.questionMembers.add(member);
    }

    public void removeQuestionMember(Member member) {
        this.questionMembers.remove(member);
    }

    public void removeAllQuestionMember() {
        this.questionMembers = new ArrayList<>();
    }

    /**
     * @return the nextQId
     */
    public String getNextQId() {
        return nextQId;
    }

    /**
     * @param nextQId the nextQId to set
     */
    public void setNextQId(String nextQId) {
        this.nextQId = nextQId;
    }

    /**
     * @return the prevQId
     */
    public String getPrevQId() {
        return prevQId;
    }

    /**
     * @param prevQId the prevQId to set
     */
    public void setPrevQId(String prevQId) {
        this.prevQId = prevQId;
    }

    /**
     * @return the questionId
     */
    public String getQuestionId() {
        return questionId;
    }

    /**
     * @param questionId the questionId to set
     */
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    /**
     * @return the answered
     */
    public boolean isAnswered() {
        return answered;
    }

    /**
     * @param answered the answered to set
     */
    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public class Member {

        private String id;
        private String name;
        private String pictureUrl;
        private boolean answered;

        public Member(String id, String name, String pictureUrl, boolean answered) {

            this.name = name;
            this.pictureUrl = pictureUrl;
            this.id = id;
            this.answered = answered;

        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPictureUrl() {
            return pictureUrl;
        }

        public void setPictureUrl(String pictureUrl) {
            this.pictureUrl = pictureUrl;
        }

        public boolean isAnswered() {
            return answered;
        }

        public void setAnswered(boolean answered) {
            this.answered = answered;
        }
    }

    public class GraphData {

        private List<Choice> data;

        public GraphData() {
            data = new ArrayList<>();
        }

        public void addData(String choice, String quantity) {

            data.add(new Choice(choice, quantity));

        }

        public class Choice {

            private String choice;
            private String NrOfChoices;

            public Choice(String choice, String NrOfChoices) {
                this.choice = choice;
                this.NrOfChoices = NrOfChoices;
            }
        };

        public String toJSON() {
            Gson gson = new Gson();

            String tmp = gson.toJson(this, GraphData.class);
            if (!tmp.isEmpty()) {
                tmp = tmp.substring(0, tmp.length() - 1);
                tmp = tmp.substring(tmp.indexOf("["), tmp.length());
            }
            return tmp;
        }

    }
    
    public String toJSON(){
    
        Gson gson = new Gson();
        
        return gson.toJson(this, QuestionData.class);
        
    }
}
