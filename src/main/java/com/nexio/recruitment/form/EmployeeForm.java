package com.nexio.recruitment.form;

import com.nexio.recruitment.model.Employee;
import com.nexio.recruitment.model.Team;
import org.apache.logging.log4j.util.Strings;
import org.springframework.validation.BindingResult;

import javax.validation.constraints.*;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeForm  {

    @NotBlank(message = "Field can not be empty")
    private String firstName;

    @NotBlank(message = "Field can not be empty")
    private String lastName;

    @NotNull(message = "Field can not be empty")
    private Employee.Position position;

    @Positive(message = "Value should positive greater than 0")
    @Digits(integer = 38, fraction = 2, message = "You may enter only 2 decimal numbers in this field")
    @NotNull(message = "Field can not be empty")
    private Float salary;

    private Long supervisorId;

    private List<Long> teams;

    public Employee parseThisToEntity(Employee employee){

        if(employee == null) {
            employee = new Employee();

            employee.setFirstName(this.firstName);
            employee.setLastName(this.lastName);
            employee.setSalary(this.salary);
            employee.setPosition(this.position);
            employee.setSupervisorId(this.supervisorId);

        }else{
            employee.setSalary(this.salary);
            employee.setPosition(this.position);
            employee.setSupervisorId(this.supervisorId);
        }
        return employee;
    }


    public EmployeeForm publishEntity(Employee employee) {

        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.position = employee.getPosition();
        this.salary = employee.getSalary();
        this.supervisorId = employee.getSupervisorId();
        this.teams = employee.getTeams().stream().map(Team::getId).collect(Collectors.toList());
        return this;
    }

    public void validateForm(BindingResult bindingResult) {

        if(Strings.isBlank(this.firstName)){
            bindingResult.rejectValue("firstName", "form.field.error.null");
        }

        if(Strings.isBlank(this.lastName)){
            bindingResult.rejectValue("lastName", "form.field.error.null");
        }

        if(this.position == null){
            bindingResult.rejectValue("position", "form.field.error.null");
        }

        if(this.salary == null){
            bindingResult.rejectValue("salary", "form.field.error.null");
        }
        if(this.salary <= 0){
            bindingResult.rejectValue("salary", "form.field.error.lessOrEqualZero");
        }
        if((this.salary  * 100) % 1 != 0){
            bindingResult.rejectValue("salary", "form.field.error.moreThanTwoDecimalNumbers");
        }

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Employee.Position getPosition() {
        return position;
    }

    public void setPosition(Employee.Position position) {
        this.position = position;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public Long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Long supervisorId) {
        this.supervisorId = supervisorId;
    }

    public List<Long> getTeams() {
        return teams;
    }

    public void setTeams(List<Long> teams) {
        this.teams = teams;
    }



}
