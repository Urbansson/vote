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
public class PollData {

    private List<pollInfo> owned;
    private List<pollInfo> member;

    public PollData() {
        owned = new ArrayList<>();
        member = new ArrayList<>();
    }

    public void addOwned(String pollTitle, String owner, String pollId) {
        this.owned.add(new pollInfo(pollTitle, owner, pollId));
    }

    public void addMember(String pollTitle, String owner, String pollId) {
        this.member.add(new pollInfo(pollTitle, owner, pollId));
    }

    /**
     * @return the owned
     */
    public List<pollInfo> getOwned() {
        return owned;
    }

    /**
     * @return the member
     */
    public List<pollInfo> getMember() {
        return member;
    }

    public class pollInfo {

        private String pollTitle;
        private String owner;
        private String pollId;

        public pollInfo(String pollTitle, String owner, String pollId) {
            this.pollTitle = pollTitle;
            this.owner = owner;
            this.pollId = pollId;
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
         * @return the owner
         */
        public String getOwner() {
            return owner;
        }

        /**
         * @param owner the owner to set
         */
        public void setOwner(String owner) {
            this.owner = owner;
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
    }
    
    public String toJSON(){
    
        Gson gson = new Gson();
        
        return gson.toJson(this, PollData.class);
        
    }
}
