/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vote.bo.persist;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Teddy
 */
@Entity
@Table(name = "MEMBER_ANSWERED")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MemberAnswered.findAll", query = "SELECT m FROM MemberAnswered m"),
    @NamedQuery(name = "MemberAnswered.findByFbId", query = "SELECT m FROM MemberAnswered m WHERE m.memberAnsweredPK.fbId = :fbId"),
    @NamedQuery(name = "MemberAnswered.findByQuestionId", query = "SELECT m FROM MemberAnswered m WHERE m.memberAnsweredPK.questionId = :questionId"),
    @NamedQuery(name = "MemberAnswered.findByAnswered", query = "SELECT m FROM MemberAnswered m WHERE m.answered = :answered")})
public class MemberAnswered implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MemberAnsweredPK memberAnsweredPK;
    @Column(name = "ANSWERED")
    private Boolean answered;
    @JoinColumn(name = "FB_ID", referencedColumnName = "FB_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TUser tUser;
    @JoinColumn(name = "QUESTION_ID", referencedColumnName = "QUESTION_ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TQuestion tQuestion;

    public MemberAnswered() {
    }

    public MemberAnswered(MemberAnsweredPK memberAnsweredPK) {
        this.memberAnsweredPK = memberAnsweredPK;
    }

    public MemberAnswered(String fbId, int questionId) {
        this.memberAnsweredPK = new MemberAnsweredPK(fbId, questionId);
    }

    public MemberAnsweredPK getMemberAnsweredPK() {
        return memberAnsweredPK;
    }

    public void setMemberAnsweredPK(MemberAnsweredPK memberAnsweredPK) {
        this.memberAnsweredPK = memberAnsweredPK;
    }

    public Boolean getAnswered() {
        return answered;
    }

    public void setAnswered(Boolean answered) {
        this.answered = answered;
    }

    public TUser getTUser() {
        return tUser;
    }

    public void setTUser(TUser tUser) {
        this.tUser = tUser;
    }

    public TQuestion getTQuestion() {
        return tQuestion;
    }

    public void setTQuestion(TQuestion tQuestion) {
        this.tQuestion = tQuestion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memberAnsweredPK != null ? memberAnsweredPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MemberAnswered)) {
            return false;
        }
        MemberAnswered other = (MemberAnswered) object;
        if ((this.memberAnsweredPK == null && other.memberAnsweredPK != null) || (this.memberAnsweredPK != null && !this.memberAnsweredPK.equals(other.memberAnsweredPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vote.bo.persist.MemberAnswered[ memberAnsweredPK=" + memberAnsweredPK + " ]";
    }
    
}
