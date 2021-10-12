package dao.entities.metamodels.embeddables;

import dao.entities.embeddables.ContactDetails;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ContactDetails.class)
public abstract class ContactDetails_ {

	public static volatile SingularAttribute<ContactDetails, String> phoneNumber;
	public static volatile SingularAttribute<ContactDetails, String> email;
	public static volatile SingularAttribute<ContactDetails, String> extraInfo;

	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String EMAIL = "email";
	public static final String EXTRA_INFO = "extraInfo";

}

