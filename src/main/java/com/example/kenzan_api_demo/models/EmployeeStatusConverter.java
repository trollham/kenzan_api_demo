package com.example.kenzan_api_demo.models;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/*
    This class handles converting from our EmployeeStatus enum to a boolean value which is stored in the database.
    This is done because of the requirement that the status field returned by the API be either ACTIVE or INACTIVE.
    Since this is a binary state, it makes sense to store it in the table as a boolean value to simplify the lookup
    for our GET endpoints, as well as easily flip the status from ACTIVE to INACTIVE.
 */
@Converter(autoApply = true)
public class EmployeeStatusConverter implements AttributeConverter<EmployeeStatus, Boolean> {

    @Override
    public Boolean convertToDatabaseColumn(EmployeeStatus employeeStatus) {

        if (employeeStatus == null) {
            return true;
        }
        return employeeStatus.getStatus();
    }

    @Override
    public EmployeeStatus convertToEntityAttribute(Boolean aBoolean) {
        if (aBoolean == null || !aBoolean) {
            return EmployeeStatus.INACTIVE;
        }
        return EmployeeStatus.ACTIVE;
    }
}
