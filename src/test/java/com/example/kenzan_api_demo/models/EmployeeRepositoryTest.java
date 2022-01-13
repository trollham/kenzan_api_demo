package com.example.kenzan_api_demo.models;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @Sql("classpath:create_employees.sql")
    void findAll() {
        ArrayList<Employee> expected = new ArrayList<Employee>();
        expected.add(new Employee(1, "Eric", "L", "Huff",
                LocalDate.of(1994, 07, 16),
                LocalDate.of(2022, 01, 01)));
        expected.add(new Employee(2, "Denzen", "", "Trillby",
                LocalDate.of(1991, 11, 23),
                LocalDate.of(2020, 04, 02)));

        List<Employee> employees = employeeRepository.findAll();
        assertEquals(employees, expected);
    }

    @Test
    @Sql("classpath:create_employees.sql")
    void findByIdAndActiveTrue() {
        Employee expected = new Employee(1, "Eric", "L", "Huff",
                LocalDate.of(1994, 07, 16),
                LocalDate.of(2022, 01, 01));
        assertEquals(expected, employeeRepository.findByIdAndActive(1).get());
    }

    @Test
    @Sql("classpath:create_employees.sql")
    void findByIdAndActiveTrueReturnsNullForInactiveAndNonexistentEmployees() {
        // test that searching for a known INACTIVE value returns Optional.empty
        assertEquals(Optional.empty(), employeeRepository.findByIdAndActive(3));
        // test that a nonexistent value also returns Optional.empty
        assertEquals(Optional.empty(), employeeRepository.findByIdAndActive(4));
    }

    @Test
    @Sql("classpath:create_employees.sql")
    void softDelete() {
        assertTrue(employeeRepository.findByIdAndActive(1).isPresent());
        employeeRepository.softDelete(1);
        assertFalse(employeeRepository.findByIdAndActive(1).isPresent());
    }

    @Test
    @Sql("classpath:create_employees.sql")
    void findByIdAndInactive() {
        Employee expected = new Employee(3, "Johnathan", "M", "Trishton",
                LocalDate.of(1994, 07, 16),
                LocalDate.of(2021, 01, 01),
                EmployeeStatus.INACTIVE);

        assertEquals(expected, employeeRepository.findByIdAndInactive(3).get());
    }

}