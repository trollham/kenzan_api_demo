package com.example.kenzan_api_demo.controllers;

import com.example.kenzan_api_demo.models.Employee;
import com.example.kenzan_api_demo.models.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RepositoryRestController
/*
    The controller for our REST API. It's annotated with `@RepositoryRestController` to get a lot of the nice defaults
    provided, mainly the generation of our CRUD endpoints for the `EmployeeRepository` as well as the automatic serialization
    of our `Employee` entities.
 */
public class EmployeeController {

    @Autowired
    private EmployeeRepository repository;

    @GetMapping("/employees/{id}")
    @ResponseBody
    /*
        While our `findAll` override on the EmployeeRepository interface works for the '/employees' endpoint, the
        '/employees/{id}' endpoint had trouble attempting to pull the data from the database, so it was extracted into its
        own handler.

        This has the chance to throw a ResponseStatusException with the 404 HTTP Status in case there weren't any ACTIVE
        employees with that ID, which is preferable to returning a string containing just the word `null`.
     */
    Employee getEmployee(@PathVariable long id) {
        Optional<Employee> employee = repository.findByIdAndActive(id);
        if (!employee.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        return employee.get();
    }

    @DeleteMapping("/employees/{id}")
    /*
        As we don't want to hard delete the rows in our employee table, we need an override for the delete method which
        calls the softDelete method defined in our repository.
     */
    ResponseEntity softDeleteEmployee(@PathVariable("id") long id) {
        repository.softDelete(id);
        return ResponseEntity.ok().build();
    }
}
