package dao.entities;

import dao.entities.embeddables.Position;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "EMPLOYEE")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "surname")
    private String surname;

    @Column(name = "name", nullable = false)
    @NotNull(message = "name should not be null")
    @Size(min = 1, message = "name should be at least 1 character long")
    private String name;

    @Column(name = "patronymic")
    private String patronymic;

    @Embedded
    private Position position;

//    @ManyToMany(mappedBy = "executors", fetch = FetchType.LAZY)
//    private Set<Assignment> assignments;

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

//    public Set<Assignment> getAssignments() {
//        return assignments;
//    }

//    public void setAssignments(Set<Assignment> assignments) {
//        this.assignments = assignments;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) &&
                Objects.equals(surname, employee.surname) &&
                Objects.equals(name, employee.name) &&
                Objects.equals(patronymic, employee.patronymic) &&
                Objects.equals(position, employee.position);
//                Objects.equals(assignments, employee.assignments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,
                surname,
                name,
                patronymic,
                position);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", position=" + position +
                '}';
    }
}
