package dao.entities.metamodels.embeddables;

import dao.entities.Company;
import dao.entities.Department;
import dao.entities.embeddables.Position;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Position.class)
public abstract class Position_ {

	public static volatile SingularAttribute<Position, String> positionTitle;
	public static volatile SingularAttribute<Position, Company> company;
	public static volatile SingularAttribute<Position, Department> department;

	public static final String POSITION_TITLE = "positionTitle";
	public static final String COMPANY = "company";
	public static final String DEPARTMENT = "department";

}

