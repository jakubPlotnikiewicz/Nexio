package com.nexio.recruitment.service;

import com.nexio.recruitment.form.TeamForm;
import com.nexio.recruitment.model.Employee;
import com.nexio.recruitment.model.Team;
import com.nexio.recruitment.repository.EmployeeRepository;
import com.nexio.recruitment.repository.TeamRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }

    public List<Team> getTeamsByPhrase(String search) {
        return teamRepository.findByPhrase("%" + search + "%");
    }

    public void saveTeam(TeamForm teamForm) {

        Team team = teamForm.parseThisToEntity(null);
        List<Long> teamMembersIds = getIdsOfTeamMembers(team);
        if(teamMembersIds.size() > 0) {
            List<Employee> teamMembers = findTeamMembers(teamMembersIds);
            team.getEmployees().addAll(teamMembers);
        }
        teamRepository.save(team);
    }

    public Team getTeamById(Long teamId) {

        return teamRepository.getOne(teamId);
    }

    public void updateTeam(Long teamId, TeamForm teamForm) {

        List<Long> newTeamMemberrsIds = new ArrayList<>();
        Team team = teamRepository.getOne(teamId);

        if(teamForm.getProjectManagerId() != team.getProjectManagerId()){
            team.getEmployees().remove(team.getProjectManagerId());
            newTeamMemberrsIds.add(teamForm.getProjectManagerId());
            team.setProjectManagerId(teamForm.getProjectManagerId());
        }
        if(teamForm.getProjectOwnerId() != team.getProjectOwnerId()){
            team.getEmployees().remove(team.getProjectOwnerId());
            newTeamMemberrsIds.add(teamForm.getProjectOwnerId());
            team.setProjectOwnerId(teamForm.getProjectOwnerId());
        }
        if( team.getScrumMaster() == null && teamForm.getScrumMasterId() != null) {
            team.setScrumMasterId(teamForm.getScrumMasterId());
            newTeamMemberrsIds.add(teamForm.getScrumMasterId());
        }else if(team.getScrumMaster() != null && teamForm.getScrumMasterId() == null){
            team.setScrumMasterId(null);
            team.getEmployees().remove(team.getScrumMaster());
        }else if(team.getScrumMasterId() != teamForm.getScrumMasterId()){
            team.getEmployees().remove(team.getScrumMaster());
            newTeamMemberrsIds.add(teamForm.getScrumMasterId());
            team.setScrumMasterId(teamForm.getScrumMasterId());
        }
        if(newTeamMemberrsIds.size() > 0) {
            List<Employee> newTeamMembers = findTeamMembers(newTeamMemberrsIds);
            team.getEmployees().addAll(newTeamMembers);
        }

        teamRepository.save(team);
    }

    private List<Long> getIdsOfTeamMembers(Team team) {
        List<Long> ids = new ArrayList<>();
        if(team.getProjectManagerId() != null){
            ids.add(team.getProjectManagerId());
        }
        if(team.getProjectOwnerId() != null) {
            ids.add(team.getProjectOwnerId());
        }
        if(team.getScrumMasterId() != null) {
            ids.add(team.getScrumMasterId());
        }
        return ids;
    }

    private List<Employee> findTeamMembers(List<Long> ids){
        List<Employee> employees = employeeRepository.findAllById(ids);
        return employees;
    }

}
