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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "T_USER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TUser.findAll", query = "SELECT t FROM TUser t"),
    @NamedQuery(name = "TUser.findByFbId", query = "SELECT t FROM TUser t WHERE t.fbId = :fbId"),
    @NamedQuery(name = "TUser.findByFbToken", query = "SELECT t FROM TUser t WHERE t.fbToken = :fbToken"),
    @NamedQuery(name = "TUser.findByInfo", query = "SELECT t FROM TUser t WHERE t.info = :info")})
public class TUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "FB_ID")
    private String fbId;
    @Size(max = 255)
    @Column(name = "FB_TOKEN")
    private String fbToken;
    @Size(max = 128)
    @Column(name = "INFO")
    private String info;
    @JoinTable(name = "POLL_MEMBERS", joinColumns = {
        @JoinColumn(name = "FB_ID", referencedColumnName = "FB_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "POLL_ID", referencedColumnName = "POLL_ID")})
    @ManyToMany
    private Collection<TPoll> tPollCollection;
    @OneToMany(mappedBy = "deviceOwner")
    private Collection<TDevice> tDeviceCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tUser")
    private Collection<MemberAnswered> memberAnsweredCollection;
    @OneToMany(mappedBy = "pollOwner")
    private Collection<TPoll> tPollCollection1;

    public TUser() {
    }

    public TUser(String fbId) {
        this.fbId = fbId;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getFbToken() {
        return fbToken;
    }

    public void setFbToken(String fbToken) {
        this.fbToken = fbToken;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @XmlTransient
    public Collection<TPoll> getTPollCollection() {
        return tPollCollection;
    }

    public void setTPollCollection(Collection<TPoll> tPollCollection) {
        this.tPollCollection = tPollCollection;
    }

    @XmlTransient
    public Collection<TDevice> getTDeviceCollection() {
        return tDeviceCollection;
    }

    public void setTDeviceCollection(Collection<TDevice> tDeviceCollection) {
        this.tDeviceCollection = tDeviceCollection;
    }

    @XmlTransient
    public Collection<MemberAnswered> getMemberAnsweredCollection() {
        return memberAnsweredCollection;
    }

    public void setMemberAnsweredCollection(Collection<MemberAnswered> memberAnsweredCollection) {
        this.memberAnsweredCollection = memberAnsweredCollection;
    }

    @XmlTransient
    public Collection<TPoll> getTPollCollection1() {
        return tPollCollection1;
    }

    public void setTPollCollection1(Collection<TPoll> tPollCollection1) {
        this.tPollCollection1 = tPollCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fbId != null ? fbId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TUser)) {
            return false;
        }
        TUser other = (TUser) object;
        if ((this.fbId == null && other.fbId != null) || (this.fbId != null && !this.fbId.equals(other.fbId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vote.bo.persist.TUser[ fbId=" + fbId + " ]";
    }
    
}
