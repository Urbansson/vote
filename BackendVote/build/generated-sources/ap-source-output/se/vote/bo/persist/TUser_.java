package se.vote.bo.persist;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import se.vote.bo.persist.MemberAnswered;
import se.vote.bo.persist.TDevice;
import se.vote.bo.persist.TPoll;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-06T23:07:11")
@StaticMetamodel(TUser.class)
public class TUser_ { 

    public static volatile CollectionAttribute<TUser, TPoll> tPollCollection1;
    public static volatile SingularAttribute<TUser, String> fbToken;
    public static volatile SingularAttribute<TUser, String> fbId;
    public static volatile CollectionAttribute<TUser, MemberAnswered> memberAnsweredCollection;
    public static volatile CollectionAttribute<TUser, TPoll> tPollCollection;
    public static volatile CollectionAttribute<TUser, TDevice> tDeviceCollection;
    public static volatile SingularAttribute<TUser, String> info;

}