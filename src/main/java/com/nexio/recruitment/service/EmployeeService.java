package com.nexio.recruitment.service;

import com.nexio.recruitment.exception.BadSalaryValueException;
import com.nexio.recruitment.form.EmployeeForm;
import com.nexio.recruitment.model.Employee;
import com.nexio.recruitment.model.Team;
import com.nexio.recruitment.repository.EmployeeRepository;
import com.nexio.recruitment.repository.TeamRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    TeamRepository teamRepository;

    public void saveEmployee(EmployeeForm employeeForm) {
        
        Employee employee = employeeForm.parseThisToEntity(null);
        if(employeeForm.getTeams().size() > 0) {
            List<Team> teams = teamRepository.findByIds(employeeForm.getTeams());
            employee.setTeams(teams);
        }
        employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployesByPostion(Employee.Position position){
        return employeeRepository.findByPosition(position);
    }

    public EmployeeForm getEmployeeById(Long employeId) {
        EmployeeForm employeeForm = new EmployeeForm();
        return employeeForm.publishEntity(employeeRepository.getOne(employeId));
    }

    public void updateEmployee(Long employeeId, EmployeeForm employeeForm) {

        Employee employee = employeeRepository.getOne(employeeId);
        employee = employeeForm.parseThisToEntity(employee);
        if(employeeForm.getTeams().size() > 0) {
            List<Team> teams = teamRepository.findByIds(employeeForm.getTeams());
            employee.setTeams(teams);
        }
        employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees(){
        return  employeeRepository.findAll();
    }

    public List<Employee> getEmployeesByPhrase(String search) {
        return employeeRepository.findByPhrase("%" + search + "%");
    }

    public List<Employee> getAllEmployeesByPositionForEdit(Employee.Position position, Long employeId) {
        return employeeRepository.findByPositionUnlessEqualId(position, employeId);
    }

    public void throwExceptionIfValueOfSalaryIsEqualOrLessThanZero(BindingResult bindingResult){
        if(bindingResult.hasFieldErrors("salary")){
            FieldError fieldError = bindingResult.getFieldError("salary");
            if(fieldError == null){
                return;
            }
            Float value = (Float) fieldError.getRejectedValue();
            if(value == null || value <= 0){
                try {
                    throwBadSalaryValueException();
                }catch (BadSalaryValueException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void throwBadSalaryValueException() {
        throw new BadSalaryValueException("Salary Value must be greater than 0");
    }


}
