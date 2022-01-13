package com.example.kenzan_api_demo.models;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    /*
        Overrides the basic `findAll` from the CrudRepository to force only returning employees that are ACTIVE
     */
    @Override
    @Query("select e from #{#entityName} e where e.status=com.example.kenzan_api_demo.models.EmployeeStatus.ACTIVE")
    List<Employee> findAll();

    @Query("select e from #{#entityName} e where e.id = ?1 and e.status=com.example.kenzan_api_demo.models.EmployeeStatus.ACTIVE")
    Optional<Employee> findByIdAndActive(long id);

    @Query("select e from #{#entityName} e where e.id = ?1 and e.status=com.example.kenzan_api_demo.models.EmployeeStatus.INACTIVE")
    Optional<Employee> findByIdAndInactive(long id);

    /*
        Because we don't want deletes to remove data from our table, softDelete is defined as an update on the table to set
        status flag to INACTIVE
     */
    @Transactional
    @Modifying
    @Query("update #{#entityName} e set e.status=com.example.kenzan_api_demo.models.EmployeeStatus.INACTIVE where e.id=?1")
    void softDelete(long id);
}
