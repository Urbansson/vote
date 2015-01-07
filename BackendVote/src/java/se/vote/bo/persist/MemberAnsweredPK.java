/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vote.bo.persist;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Teddy
 */
@Embeddable
public class MemberAnsweredPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "FB_ID")
    private String fbId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUESTION_ID")
    private int questionId;

    public MemberAnsweredPK() {
    }

    public MemberAnsweredPK(String fbId, int questionId) {
        this.fbId = fbId;
        this.questionId = questionId;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fbId != null ? fbId.hashCode() : 0);
        hash += (int) questionId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MemberAnsweredPK)) {
            return false;
        }
        MemberAnsweredPK other = (MemberAnsweredPK) object;
        if ((this.fbId == null && other.fbId != null) || (this.fbId != null && !this.fbId.equals(other.fbId))) {
            return false;
        }
        if (this.questionId != other.questionId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vote.bo.persist.MemberAnsweredPK[ fbId=" + fbId + ", questionId=" + questionId + " ]";
    }
    
}
