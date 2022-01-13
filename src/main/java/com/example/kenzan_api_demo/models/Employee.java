package com.example.kenzan_api_demo.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

/*
The model of an employee. It's annotated with `@Entity` to tie into the JPA persistance layer. Each member defined in
the class becomes a column in the Employee table.
 */
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String middleInitial;
    private String lastName;
    private LocalDate dateOfBirth;
    private LocalDate dateOfEmployment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && Objects.equals(getFirstName(), employee.getFirstName()) && Objects.equals(getMiddleInitial(), employee.getMiddleInitial()) && Objects.equals(getLastName(), employee.getLastName()) && Objects.equals(getDateOfBirth(), employee.getDateOfBirth()) && Objects.equals(getDateOfEmployment(), employee.getDateOfEmployment()) && getStatus() == employee.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getFirstName(), getMiddleInitial(), getLastName(), getDateOfBirth(), getDateOfEmployment(), getStatus());
    }

    /*
            `status` is treated specially by the Spring framework thanks to the EmployeeStatusConverter. At the moment,
            there are only two states an employee can be in, ACTIVE and INACTIVE. For more information, read the documentation
            in the EmployeeStatusConverter class.
         */
    @Column(name="is_active")
    private EmployeeStatus status;

    public Employee(long id, String firstName, String middleInitial, String lastName, LocalDate dateOfBirth,
                    LocalDate dateOfEmployment, EmployeeStatus status) {
        this.id = id;
        setFirstName(firstName);
        setMiddleInitial(middleInitial);
        setLastName(lastName);
        setDateOfBirth(dateOfBirth);
        setDateOfEmployment(dateOfEmployment);
        setStatus(status);
    }

    public Employee(long id, String firstName, String middleInitial, String lastName, LocalDate dateOfBirth,
                    LocalDate dateOfEmployment) {
        this.id = id;
        setFirstName(firstName);
        setMiddleInitial(middleInitial);
        setLastName(lastName);
        setDateOfBirth(dateOfBirth);
        setDateOfEmployment(dateOfEmployment);
        setStatus(EmployeeStatus.ACTIVE);
    }

    protected Employee() {
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public LocalDate getDateOfEmployment() {
        return dateOfEmployment;
    }

    public void setDateOfEmployment(LocalDate dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
