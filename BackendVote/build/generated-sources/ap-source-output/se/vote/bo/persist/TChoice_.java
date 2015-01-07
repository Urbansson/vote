package se.vote.bo.persist;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import se.vote.bo.persist.TQuestion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-06T23:07:11")
@StaticMetamodel(TChoice.class)
public class TChoice_ { 

    public static volatile SingularAttribute<TChoice, Integer> choiceId;
    public static volatile SingularAttribute<TChoice, Integer> nrOfChoices;
    public static volatile SingularAttribute<TChoice, TQuestion> questionId;
    public static volatile SingularAttribute<TChoice, Integer> choiceNr;
    public static volatile SingularAttribute<TChoice, String> choice;

}