package dao.entities;

import dao.entities.embeddables.ContactDetails;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Objects;

@Entity
@Table(name = "DEPARTMENT")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title", length = 512, nullable = false)
    @NotNull(message = "title should not be null")
    private String title;

    @Embedded
    private ContactDetails contactDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_id")
    private Employee director;

    public Department() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    @XmlTransient
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @XmlTransient
    public Employee getDirector() {
        return director;
    }

    public void setDirector(Employee director) {
        this.director = director;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(contactDetails, that.contactDetails) &&
                Objects.equals(
                        director != null ? director.getId() : null,
                        that.director != null ? that.director.getId() : null) &&
                Objects.equals(
                        company != null ? company.getId() : null,
                        that.company != null ? that.company.getId() : null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,
                title,
                contactDetails,
                company != null ? company.getId() : null,
                director != null ? director.getId() : null);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contactDetails=" + contactDetails +
                ", company=" + (company != null ? company.getTitle() : "null") +
                ", director=" + (director != null ? director.getSurname() : "null") +
                '}';
    }

    @XmlElement(nillable = true)
    private Long getDirectorId() {
        return director != null ? director.getId() : null;
    }

    @XmlElement(nillable = true)
    private Long getCompanyId() {
        return company != null ? company.getId() : null;
    }

    @XmlElement(nillable = true)
    private void setDirectorId(Long directorId) {
        if (directorId == null) {
            this.director = null;
            return;
        }
        this.director = new Employee();
        this.director.setId(directorId);
    }

    @XmlElement(nillable = true)
    private void setCompanyId(Long companyId) {
        if (companyId == null) {
            this.company = null;
            return;
        }
        this.company = new Company();
        this.company.setId(companyId);
    }
}
