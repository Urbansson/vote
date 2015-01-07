/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vote.bo.persist;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Teddy
 */
@Entity
@Table(name = "T_CHOICE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TChoice.findAll", query = "SELECT t FROM TChoice t"),
    @NamedQuery(name = "TChoice.findByChoiceId", query = "SELECT t FROM TChoice t WHERE t.choiceId = :choiceId"),
    @NamedQuery(name = "TChoice.findByChoiceNr", query = "SELECT t FROM TChoice t WHERE t.choiceNr = :choiceNr"),
    @NamedQuery(name = "TChoice.findByChoice", query = "SELECT t FROM TChoice t WHERE t.choice = :choice"),
    @NamedQuery(name = "TChoice.findbyQuestion", query = "SELECT t FROM TChoice t WHERE t.questionId = :questionId order by t.choiceNr asc"),
    @NamedQuery(name = "TChoice.findbyQuestionChoice", query = "SELECT t FROM TChoice t WHERE t.questionId = :questionId AND t.choiceNr = :choiceNr"),
    @NamedQuery(name = "TChoice.findByNrOfChoices", query = "SELECT t FROM TChoice t WHERE t.nrOfChoices = :nrOfChoices")})
public class TChoice implements Serializable {
  
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CHOICE_ID")
    private Integer choiceId;
    @Column(name = "CHOICE_NR")
    private Integer choiceNr;
    @Size(max = 32)
    @Column(name = "CHOICE")
    private String choice;
    @Column(name = "NR_OF_CHOICES")
    private Integer nrOfChoices;
    @JoinColumn(name = "QUESTION_ID", referencedColumnName = "QUESTION_ID")
    @ManyToOne
    private TQuestion questionId;

    public TChoice() {
    }

    public TChoice(Integer choiceId) {
        this.choiceId = choiceId;
    }

    public Integer getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Integer choiceId) {
        this.choiceId = choiceId;
    }

    public Integer getChoiceNr() {
        return choiceNr;
    }

    public void setChoiceNr(Integer choiceNr) {
        this.choiceNr = choiceNr;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Integer getNrOfChoices() {
        return nrOfChoices;
    }

    public void setNrOfChoices(Integer nrOfChoices) {
        this.nrOfChoices = nrOfChoices;
    }

    public TQuestion getQuestionId() {
        return questionId;
    }

    public void setQuestionId(TQuestion questionId) {
        this.questionId = questionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (choiceId != null ? choiceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TChoice)) {
            return false;
        }
        TChoice other = (TChoice) object;
        if ((this.choiceId == null && other.choiceId != null) || (this.choiceId != null && !this.choiceId.equals(other.choiceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vote.bo.persist.TChoice[ choiceId=" + choiceId + " ]";
    }

}
