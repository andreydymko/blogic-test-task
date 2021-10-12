package dao.entities.metamodels;

import dao.entities.Company;
import dao.entities.Employee;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Company.class)
public abstract class Company_ {

	public static volatile SingularAttribute<Company, Employee> director;
	public static volatile SingularAttribute<Company, String> addressPhysical;
	public static volatile SingularAttribute<Company, Long> id;
	public static volatile SingularAttribute<Company, String> addressLegal;
	public static volatile SingularAttribute<Company, String> title;

	public static final String DIRECTOR = "director";
	public static final String ADDRESS_PHYSICAL = "addressPhysical";
	public static final String ID = "id";
	public static final String ADDRESS_LEGAL = "addressLegal";
	public static final String TITLE = "title";

}

