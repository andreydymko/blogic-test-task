package dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "ASSIGNMENT")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "subject", nullable = false)
    @NotNull(message = "subject should not be null")
    @Size(min = 1, message = "subject length should be at least 1")
    private String subject;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @NotNull(message = "author should not be null")
    private Employee author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ASSIGNMENT_EXECUTOR_EMPLOYEE",
            joinColumns = @JoinColumn(name = "assignment_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private Set<Employee> executors;

    @Column(name = "deadline_date")
    @Future(message = "deadline should be in the future")
    private Date deadlineDate;

    /*
    Assignment states and lifecycle:

                    isOnExecution     isExecuted      isControlled
    preparation:        false            false            false
    execution:          true             false            false
    control:            false            true             false
    revision:           false            false            true
    acceptation:        false            true             true

    0 --> preparation --> execution --> control --> acceptation --> 0
                              ^            |
                              |            |
                              |            V
                          revision <-------
     */

    @Column(name = "is_on_execution", nullable = false)
    @NotNull
    private Boolean isOnExecution;

    @Column(name = "is_executed", nullable = false)
    @NotNull
    private Boolean isExecuted;

    @Column(name = "is_controlled", nullable = false)
    @NotNull
    private Boolean isControlled;

    @Lob
    @Column(name = "assignment_text", nullable = false)
    private String assignmentText;

    public Assignment() {
        sendOnPreparation();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @XmlTransient
    public Employee getAuthor() {
        return author;
    }

    public void setAuthor(Employee author) {
        this.author = author;
    }

    @XmlTransient
    public Set<Employee> getExecutors() {
        return executors;
    }

    public void setExecutors(Set<Employee> executors) {
        this.executors = executors;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public Boolean getOnExecution() {
        return isOnExecution;
    }

    public void setOnExecution(Boolean onExecution) {
        isOnExecution = onExecution;
    }

    public Boolean getExecuted() {
        return isExecuted;
    }

    public void setExecuted(Boolean executed) {
        isExecuted = executed;
    }

    public Boolean getControlled() {
        return isControlled;
    }

    public void setControlled(Boolean controlled) {
        isControlled = controlled;
    }

    private void sendOnPreparation() {
        isOnExecution = false;
        isExecuted = false;
        isControlled = false;
    }

    public void sendOnExecution() {
        isOnExecution = true;
        isExecuted = false;
        isControlled = false;
    }

    public void sendOnControl() {
        isOnExecution = false;
        isExecuted = true;
        isControlled = false;
    }

    public void sendOnRevision() {
        isOnExecution = false;
        isExecuted = false;
        isControlled = true;
    }

    public void sendOnAcceptation() {
        isOnExecution = false;
        isExecuted = true;
        isControlled = true;
    }

    public String getAssignmentText() {
        return assignmentText;
    }

    public void setAssignmentText(String assignmentText) {
        this.assignmentText = assignmentText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(author, that.author) &&
                Objects.equals(executors, that.executors) &&
                Objects.equals(deadlineDate, that.deadlineDate) &&
                Objects.equals(isControlled, that.isControlled) &&
                Objects.equals(isExecuted, that.isExecuted) &&
                Objects.equals(assignmentText, that.assignmentText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subject, author, executors, deadlineDate, isControlled, isExecuted, assignmentText);
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", author=" + author +
                ", executors=" + executors +
                ", deadlineDate=" + deadlineDate +
                ", isOnExecution=" + isOnExecution +
                ", isExecuted=" + isExecuted +
                ", isControlled=" + isControlled +
                ", assignmentText='" + assignmentText + '\'' +
                '}';
    }

    @XmlElement(nillable = true)
    private Long getAuthorId() {
        return author != null ? author.getId() : null;
    }

    @XmlElement(nillable = true)
    private List<Long> getExecutorsIds() {
        return executors != null ? executors.stream().map(Employee::getId).collect(Collectors.toList()) : new ArrayList<>();
    }

    @XmlElement(nillable = true)
    private void setAuthorId(Long authorId) {
        if (authorId == null) {
            this.author = null;
            return;
        }
        this.author = new Employee();
        this.author.setId(authorId);
    }

    @XmlElement(nillable = true)
    private void setExecutorsIds(List<Long> executorsIds) {
//        if (executorsIds == null) {
//            this.executors = null;
//            return;
//        }

        this.executors = new HashSet<>(executorsIds.size());
        Iterator<Long> idsIter = executorsIds.iterator();
        Employee employee;

        while (idsIter.hasNext()) {
            employee = new Employee();
            employee.setId(idsIter.next());
            executors.add(employee);
        }
    }
}
