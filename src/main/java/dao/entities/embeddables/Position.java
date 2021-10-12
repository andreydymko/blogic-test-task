package dao.entities.embeddables;

import dao.entities.Company;
import dao.entities.Department;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Objects;

@Embeddable
public class Position {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "position_title")
    private String positionTitle;

    public Position() {
    }

    public Company getCompany() {
        return company;
    }

    @XmlTransient
    public void setCompany(Company company) {
        this.company = company;
    }

    @XmlTransient
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(
                    company != null ? company.getId() : null,
                    position.company != null ? position.company.getId() : null) &&
                Objects.equals(
                        department != null ? department.getId() : null,
                        position.department != null ? position.department.getId() : null) &&
                Objects.equals(positionTitle, position.positionTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                company != null ? company.getId() : null,
                department != null ? department.getId() : null,
                positionTitle);
    }

    @Override
    public String toString() {
        return "Position{" +
                "company=" + (company != null ? company.getTitle() : "null") +
                ", department=" + (department != null ? department.getTitle() : "null") +
                ", positionTitle='" + positionTitle + '\'' +
                '}';
    }

    @XmlElement(nillable = true)
    private Long getCompanyId() {
        return company != null ? company.getId() : null;
    }

    @XmlElement(nillable = true)
    private Long getDepartmentId() {
        return department != null ? department.getId() : null;
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

    @XmlElement(nillable = true)
    private void setDepartmentId(Long departmentId) {
        if (departmentId == null) {
            this.department = null;
            return;
        }
        this.department = new Department();
        this.department.setId(departmentId);
    }
}
