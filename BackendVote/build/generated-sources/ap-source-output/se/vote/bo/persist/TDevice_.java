package se.vote.bo.persist;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import se.vote.bo.persist.TUser;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-06T23:07:11")
@StaticMetamodel(TDevice.class)
public class TDevice_ { 

    public static volatile SingularAttribute<TDevice, TUser> deviceOwner;
    public static volatile SingularAttribute<TDevice, Integer> deviceId;
    public static volatile SingularAttribute<TDevice, String> deviceInfo;
    public static volatile SingularAttribute<TDevice, String> deviceToken;

}