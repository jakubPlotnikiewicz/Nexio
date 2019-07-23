package com.nexio.recruitment.controller;

import com.nexio.recruitment.form.TeamForm;
import com.nexio.recruitment.model.Employee;
import com.nexio.recruitment.model.Team;
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
@RequestMapping("/team")
public class TeamController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    TeamService teamService;

    @GetMapping("/all")
    public String getAll(@PathParam("search") String search, Model model){
        List<Team> teams;

        if(Strings.isBlank(search)) {
            teams = teamService.getAllTeams();
        }else{
            teams = teamService.getTeamsByPhrase(search);
        }

        model.addAttribute("teams", teams);

        return "team_table";
    }

    @GetMapping("/new")
    public String newTeam(Model model){

        model.addAttribute("teamForm", new TeamForm());
        model.addAttribute("poEmployees", employeeService.getAllEmployesByPostion(Employee.Position.PO));
        model.addAttribute("pmEmployees", employeeService.getAllEmployesByPostion(Employee.Position.PM));
        model.addAttribute("scrummasterEmployees", employeeService.getAllEmployesByPostion(Employee.Position.SCRUMASTER));

        return "team_form";
    }

    @PostMapping("/new")
    public String addTeam(@Valid TeamForm teamForm, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()){
            model.addAttribute("poEmployees", employeeService.getAllEmployesByPostion(Employee.Position.PO));
            model.addAttribute("pmEmployees", employeeService.getAllEmployesByPostion(Employee.Position.PM));
            model.addAttribute("scrummasterEmployees", employeeService.getAllEmployesByPostion(Employee.Position.SCRUMASTER));
            return "team_form";
        }

        teamService.saveTeam(teamForm);
        return "redirect:/team/all" ;
    }

    @GetMapping("/{teamId}")
    public String getTeam(@PathVariable("teamId") Long teamId, Model model){

        model.addAttribute("teamForm", teamService.getTeamById(teamId));
        model.addAttribute("poEmployees", employeeService.getAllEmployesByPostion(Employee.Position.PO));
        model.addAttribute("pmEmployees", employeeService.getAllEmployesByPostion(Employee.Position.PM));
        model.addAttribute("scrummasterEmployees", employeeService.getAllEmployesByPostion(Employee.Position.SCRUMASTER));

        return "team_form";
    }

    @PostMapping("/{teamId}")
    public String updateTeam(@PathVariable("teamId") Long teamId, @Valid TeamForm teamForm, BindingResult bindingResult){
        teamForm.validateForm(bindingResult);
        teamService.updateTeam(teamId, teamForm);

        return "redirect:/team/all" ;
    }


}
