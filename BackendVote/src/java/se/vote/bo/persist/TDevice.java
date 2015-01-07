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
@Table(name = "T_DEVICE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TDevice.findAll", query = "SELECT t FROM TDevice t"),
    @NamedQuery(name = "TDevice.findByDeviceId", query = "SELECT t FROM TDevice t WHERE t.deviceId = :deviceId"),
    @NamedQuery(name = "TDevice.findByDeviceToken", query = "SELECT t FROM TDevice t WHERE t.deviceToken = :deviceToken"),
    @NamedQuery(name = "TDevice.findByDeviceInfo", query = "SELECT t FROM TDevice t WHERE t.deviceInfo = :deviceInfo")})
public class TDevice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DEVICE_ID")
    private Integer deviceId;
    @Size(max = 32)
    @Column(name = "DEVICE_TOKEN")
    private String deviceToken;
    @Size(max = 128)
    @Column(name = "DEVICE_INFO")
    private String deviceInfo;
    @JoinColumn(name = "DEVICE_OWNER", referencedColumnName = "FB_ID")
    @ManyToOne
    private TUser deviceOwner;

    public TDevice() {
    }

    public TDevice(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public TUser getDeviceOwner() {
        return deviceOwner;
    }

    public void setDeviceOwner(TUser deviceOwner) {
        this.deviceOwner = deviceOwner;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deviceId != null ? deviceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TDevice)) {
            return false;
        }
        TDevice other = (TDevice) object;
        if ((this.deviceId == null && other.deviceId != null) || (this.deviceId != null && !this.deviceId.equals(other.deviceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.vote.bo.persist.TDevice[ deviceId=" + deviceId + " ]";
    }
    
}
