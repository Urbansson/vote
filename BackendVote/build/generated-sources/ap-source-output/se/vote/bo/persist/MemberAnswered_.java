package se.vote.bo.persist;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import se.vote.bo.persist.MemberAnsweredPK;
import se.vote.bo.persist.TQuestion;
import se.vote.bo.persist.TUser;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-06T23:07:11")
@StaticMetamodel(MemberAnswered.class)
public class MemberAnswered_ { 

    public static volatile SingularAttribute<MemberAnswered, Boolean> answered;
    public static volatile SingularAttribute<MemberAnswered, TUser> tUser;
    public static volatile SingularAttribute<MemberAnswered, TQuestion> tQuestion;
    public static volatile SingularAttribute<MemberAnswered, MemberAnsweredPK> memberAnsweredPK;

}