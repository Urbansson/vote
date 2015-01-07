/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.vote.bo.persist;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Teddy
 */
@Entity
@Table(name = "T_POLL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TPoll.findAll", query = "SELECT t FROM TPoll t"),
    @NamedQuery(name = "TPoll.findByPollId", query = "SELECT t FROM TPoll t WHERE t.pollId = :pollId"),
    @NamedQuery(name = "TPoll.findByTitle", query = "SELECT t FROM TPoll t WHERE t.title = :title"),
    @NamedQuery(name = "TPoll.findByInfo", query = "SELECT t FROM TPoll t WHERE t.info = :info")})
public class TPoll implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "POLL_ID")
    private Integer pollId;
    @Size(max = 32)
    @Column(name = "TITLE")
    private String title;
    @Size(max = 128)
    @Column(name = "INFO")
    private String info;
    @ManyToMany(mappedBy = "tPollCollection")
    private Collection<TUser> tUserCollection;
    @OneToMany(mappedBy = "pollId")
    private Collection<TQuestion> tQuestionCollection;
    @JoinColumn(name = "POLL_OWNER", referencedColumnName = "FB_ID")
    @ManyToOne
    private TUser pollOwner;

    public TPoll() {
    }

    public TPoll(Integer pollId) {
        this.pollId = pollId;
    }

    public Integer getPollId() {
        return pollId;
    }

    public void setPollId(Integer pollId) {
        this.pollId = pollId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @XmlTransient
    public Collection<TUser> getTUserCollection() {
        return tUserCollection;
    }

    public void setTUserCollection(Collection<TUser> tUserCollection) {
        this.tUserCollection = tUserCollection;
    }

    @XmlTransient
    public Collection<TQuestion> getTQuestionCollection() {
        return tQuestionCollection;
    }

    public void setTQuestionCollection(Collection<TQuestion> tQuestionCollection) {
        this.tQuestionCollection = tQuestionCollection;
    }

    public TUser getPollOwner() {
        return pollOwner;
    }

    public void setPollOwner(TUser pollOwner) {
        this.pollOwner = pollOwner;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pollId != null ? pollId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TPoll)) {
            return false;
        }
        TPoll other = (TPoll) object;
        if ((this.pollId == null && other.pollId != null) || (this.pollId != null && !this.pollId.equals(other.pollId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vote.bo.persist.TPoll[ pollId=" + pollId + " ]";
    }
    
}
