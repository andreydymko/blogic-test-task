package dao.entities.metamodels;

import dao.entities.Company;
import dao.entities.Department;
import dao.entities.Employee;
import dao.entities.embeddables.ContactDetails;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Department.class)
public abstract class Department_ {

	public static volatile SingularAttribute<Department, Employee> director;
	public static volatile SingularAttribute<Department, Company> company;
	public static volatile SingularAttribute<Department, Long> id;
	public static volatile SingularAttribute<Department, String> title;
	public static volatile SingularAttribute<Department, ContactDetails> contactDetails;

	public static final String DIRECTOR = "director";
	public static final String COMPANY = "company";
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String CONTACT_DETAILS = "contactDetails";

}

