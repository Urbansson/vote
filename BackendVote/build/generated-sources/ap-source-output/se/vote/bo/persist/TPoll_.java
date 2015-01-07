package se.vote.bo.persist;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import se.vote.bo.persist.TQuestion;
import se.vote.bo.persist.TUser;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-06T23:07:11")
@StaticMetamodel(TPoll.class)
public class TPoll_ { 

    public static volatile CollectionAttribute<TPoll, TQuestion> tQuestionCollection;
    public static volatile SingularAttribute<TPoll, TUser> pollOwner;
    public static volatile SingularAttribute<TPoll, Integer> pollId;
    public static volatile SingularAttribute<TPoll, String> title;
    public static volatile CollectionAttribute<TPoll, TUser> tUserCollection;
    public static volatile SingularAttribute<TPoll, String> info;

}