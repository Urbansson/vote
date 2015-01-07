/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vote.bo.persist;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Teddy
 */
@Entity
@Table(name = "T_QUESTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TQuestion.findAll", query = "SELECT t FROM TQuestion t"),
    @NamedQuery(name = "TQuestion.findByQuestionId", query = "SELECT t FROM TQuestion t WHERE t.questionId = :questionId"),
    @NamedQuery(name = "TQuestion.findByQuestionNr", query = "SELECT t FROM TQuestion t WHERE t.questionNr = :questionNr"),
    @NamedQuery(name = "TQuestion.findByQuestionTitle", query = "SELECT t FROM TQuestion t WHERE t.questionTitle = :questionTitle"),               
    @NamedQuery(name = "TQuestion.findNewestQuestionInPoll", query = "SELECT t FROM TQuestion t WHERE t.pollId = :pollId ORDER BY t.questionNr DESC "),
    @NamedQuery(name = "TQuestion.findByQuestion", query = "SELECT t FROM TQuestion t WHERE t.question = :question")})
public class TQuestion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "QUESTION_ID")
    private Integer questionId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUESTION_NR")
    private int questionNr;
    @Size(max = 128)
    @Column(name = "QUESTION_TITLE")
    private String questionTitle;
    @Size(max = 128)
    @Column(name = "QUESTION")
    private String question;
    @JoinColumn(name = "POLL_ID", referencedColumnName = "POLL_ID")
    @ManyToOne
    private TPoll pollId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tQuestion")
    private Collection<MemberAnswered> memberAnsweredCollection;
    @OneToMany(mappedBy = "questionId")
    private Collection<TChoice> tChoiceCollection;

    public TQuestion() {
    }

    public TQuestion(Integer questionId) {
        this.questionId = questionId;
    }

    public TQuestion(Integer questionId, int questionNr) {
        this.questionId = questionId;
        this.questionNr = questionNr;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public int getQuestionNr() {
        return questionNr;
    }

    public void setQuestionNr(int questionNr) {
        this.questionNr = questionNr;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public TPoll getPollId() {
        return pollId;
    }

    public void setPollId(TPoll pollId) {
        this.pollId = pollId;
    }

    @XmlTransient
    public Collection<MemberAnswered> getMemberAnsweredCollection() {
        return memberAnsweredCollection;
    }

    public void setMemberAnsweredCollection(Collection<MemberAnswered> memberAnsweredCollection) {
        this.memberAnsweredCollection = memberAnsweredCollection;
    }

    @XmlTransient
    public Collection<TChoice> getTChoiceCollection() {
        return tChoiceCollection;
    }

    public void setTChoiceCollection(Collection<TChoice> tChoiceCollection) {
        this.tChoiceCollection = tChoiceCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionId != null ? questionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TQuestion)) {
            return false;
        }
        TQuestion other = (TQuestion) object;
        if ((this.questionId == null && other.questionId != null) || (this.questionId != null && !this.questionId.equals(other.questionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vote.bo.persist.TQuestion[ questionId=" + questionId + " ]";
    }
    
}
