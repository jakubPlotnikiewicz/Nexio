package com.nexio.recruitment.controller;

import com.nexio.recruitment.form.EmployeeForm;
import com.nexio.recruitment.model.Employee;
import com.nexio.recruitment.service.EmployeeService;
import com.nexio.recruitment.service.TeamService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    TeamService teamService;

    @GetMapping("/all")
    public String getEmployees(@PathParam("search") String search,  Model model){
        List<Employee> employees;
        if(Strings.isBlank(search)){
            employees = employeeService.getAllEmployees();
        }else{
            employees = employeeService.getEmployeesByPhrase(search);
        }
        model.addAttribute("employees", employees);

        return "emloyee_table";
    }

    @GetMapping("/new")
    public String newEmployee(Model model){

        model.addAttribute("employeeForm", new EmployeeForm());
        model.addAttribute("pmEmployees", employeeService.getAllEmployesByPostion(Employee.Position.PM));
        model.addAttribute("teams", teamService.getAllTeams());

        return "employee_form";
    }

    @PostMapping("/new")
    public String addEmployee(@Valid @ModelAttribute("employeeForm") EmployeeForm employeeForm, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            employeeService.throwExceptionIfValueOfSalaryIsEqualOrLessThanZero(bindingResult);
            model.addAttribute("pmEmployees", employeeService.getAllEmployesByPostion(Employee.Position.PM));
            model.addAttribute("teams", teamService.getAllTeams());
            return "employee_form";
        }

        employeeService.saveEmployee(employeeForm);
        return "redirect:/employee/all" ;
    }

    @GetMapping("/{employeId}")
    public String getEmployee(@PathVariable("employeId") Long employeId, Model model){

        model.addAttribute("employeeForm", employeeService.getEmployeeById(employeId));
        model.addAttribute("pmEmployees", employeeService.getAllEmployeesByPositionForEdit(Employee.Position.PM, employeId));
        model.addAttribute("teams", teamService.getAllTeams());
        return "employee_form";
    }

    @PostMapping("/{employeId}")
    public String updateEmployee( @Valid @PathVariable("employeId") Long employeId, EmployeeForm employeeForm, BindingResult bindingResult, Model model){

        employeeForm.validateForm(bindingResult);

        if(bindingResult.hasErrors()) {
            employeeService.throwExceptionIfValueOfSalaryIsEqualOrLessThanZero(bindingResult);
            model.addAttribute("pmEmployees", employeeService.getAllEmployesByPostion(Employee.Position.PM));
            model.addAttribute("teams", teamService.getAllTeams());
            return "employee_form";
        }
        employeeService.updateEmployee(employeId, employeeForm);
        return "redirect:/employee/all";
    }
}
