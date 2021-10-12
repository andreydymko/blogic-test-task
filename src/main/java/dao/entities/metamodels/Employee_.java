package dao.entities.metamodels;

import dao.entities.Company;
import dao.entities.Employee;
import dao.entities.embeddables.Position;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Employee.class)
public abstract class Employee_ {

	public static volatile SingularAttribute<Employee, String> patronymic;
	public static volatile SingularAttribute<Employee, String> surname;
	public static volatile SingularAttribute<Employee, String> name;
	public static volatile SingularAttribute<Employee, Long> id;
	public static volatile SingularAttribute<Employee, Position> position;
	public static volatile SingularAttribute<Employee, Company> company;

	public static final String PATRONYMIC = "patronymic";
	public static final String SURNAME = "surname";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String POSITION = "position";
	public static final String COMPANY = "company";
}

