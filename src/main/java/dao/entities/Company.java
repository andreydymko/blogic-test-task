package dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Objects;

@Entity
@Table(name = "COMPANY")
@XmlRootElement
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title", length = 512, nullable = false)
    @Size(min = 1, message = "title length should be at least 1")
    @NotNull(message = "title should not be null")
    private String title;

    @Column(name = "address_physical", length = 1024)
    private String addressPhysical;

    @Column(name = "address_legal", length = 1024)
    private String addressLegal;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_id")
    private Employee director;

    public Company() {
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

    public String getAddressPhysical() {
        return addressPhysical;
    }

    public void setAddressPhysical(String addressPhysical) {
        this.addressPhysical = addressPhysical;
    }

    public String getAddressLegal() {
        return addressLegal;
    }

    public void setAddressLegal(String addressLegal) {
        this.addressLegal = addressLegal;
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
        Company company = (Company) o;
        return Objects.equals(id, company.id) &&
                Objects.equals(title, company.title) &&
                Objects.equals(addressPhysical, company.addressPhysical) &&
                Objects.equals(addressLegal, company.addressLegal) &&
                Objects.equals((director != null ? director.getId() : null),
                        (company.director != null ? company.director.getId() : null));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,
                title,
                addressPhysical,
                addressLegal,
                (director != null ? director.getId() : null));
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", addressPhysical='" + addressPhysical + '\'' +
                ", addressLegal='" + addressLegal + '\'' +
                ", director=" + (director != null ? director.getSurname() : "null") +
                '}';
    }

    public String toJson() {
        return "{" +
                "\"id\":\"" + id + '\"' +
                ", \"title=\":\"" + title + '\"' +
                ", \"addressPhysical\":\"" + addressPhysical + '\"' +
                ", \"addressLegal\":\"" + addressLegal + '\"' +
                ", \"director\":\"" + director.getId() + '\"' +
                '}';
    }

    @XmlElement(nillable = true)
    private Long getDirectorId() {
        return director != null ? director.getId() : null;
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
}
