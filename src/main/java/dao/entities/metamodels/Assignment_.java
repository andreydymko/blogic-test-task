package dao.entities.metamodels;

import dao.entities.Assignment;
import dao.entities.Employee;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Assignment.class)
public abstract class Assignment_ {

	public static volatile SingularAttribute<Assignment, Boolean> isControlled;
	public static volatile SingularAttribute<Assignment, String> assignmentText;
	public static volatile SingularAttribute<Assignment, Boolean> isExecuted;
	public static volatile SingularAttribute<Assignment, String> subject;
	public static volatile SingularAttribute<Assignment, Employee> author;
	public static volatile SingularAttribute<Assignment, Date> deadlineDate;
	public static volatile SingularAttribute<Assignment, Boolean> isOnExecution;
	public static volatile SetAttribute<Assignment, Employee> executors;
	public static volatile SingularAttribute<Assignment, Long> id;

	public static final String IS_CONTROLLED = "isControlled";
	public static final String ASSIGNMENT_TEXT = "assignmentText";
	public static final String IS_EXECUTED = "isExecuted";
	public static final String SUBJECT = "subject";
	public static final String AUTHOR = "author";
	public static final String DEADLINE_DATE = "deadlineDate";
	public static final String IS_ON_EXECUTION = "isOnExecution";
	public static final String EXECUTORS = "executors";
	public static final String ID = "id";

}

