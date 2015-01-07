package se.vote.bo.persist;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import se.vote.bo.persist.MemberAnswered;
import se.vote.bo.persist.TChoice;
import se.vote.bo.persist.TPoll;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-06T23:07:11")
@StaticMetamodel(TQuestion.class)
public class TQuestion_ { 

    public static volatile SingularAttribute<TQuestion, Integer> questionId;
    public static volatile SingularAttribute<TQuestion, String> questionTitle;
    public static volatile SingularAttribute<TQuestion, String> question;
    public static volatile CollectionAttribute<TQuestion, TChoice> tChoiceCollection;
    public static volatile SingularAttribute<TQuestion, TPoll> pollId;
    public static volatile SingularAttribute<TQuestion, Integer> questionNr;
    public static volatile CollectionAttribute<TQuestion, MemberAnswered> memberAnsweredCollection;

}