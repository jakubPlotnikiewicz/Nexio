package com.nexio.recruitment.form;

import com.nexio.recruitment.model.Team;
import org.apache.logging.log4j.util.Strings;
import org.springframework.validation.BindingResult;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TeamForm{

    @NotBlank(message = "Field can not be empty")
    String teamName;

    @NotBlank(message = "Field can not be empty")
    String projectName;

    @NotNull(message = "Field can not be empty")
    Long projectOwnerId;

    @NotNull(message = "Field can not be empty")
    Long projectManagerId;

    Long scrumMasterId;

    public Team parseThisToEntity(Team team) {

        if(team == null) {
            team = new Team();
            team.setTeamName(this.teamName);
            team.setProjectName(this.projectName);
            team.setProjectManagerId(this.projectManagerId);
            team.setProjectOwnerId(this.projectOwnerId);
            team.setScrumMasterId(this.scrumMasterId);
        }else{
            team.setProjectManagerId(this.projectManagerId);
            team.setProjectOwnerId(this.projectOwnerId);
            team.setScrumMasterId(this.scrumMasterId);
        }

        return team;
    }

    public TeamForm publishEntity(Team team) {

        this.teamName = team.getTeamName();
        this.projectName = team.getProjectName();
        this.projectManagerId = team.getProjectManagerId();
        this.projectOwnerId = team.getProjectOwnerId();
        this.scrumMasterId = team.getScrumMasterId();


        return this;
    }

    public void validateForm(BindingResult bindingResult) {

        if(Strings.isBlank(this.teamName)){
            bindingResult.rejectValue("teamName", "form.field.error.null");
        }

        if(Strings.isBlank(this.projectName)){
            bindingResult.rejectValue("projectName", "form.field.error.null");
        }

        if(this.projectManagerId == null){
            bindingResult.rejectValue("projectManagerId", "form.field.error.null");
        }

        if(this.projectOwnerId == null){
            bindingResult.rejectValue("projectOwnerId", "form.field.error.null");
        }
        if(this.scrumMasterId <= 0){
            bindingResult.rejectValue("scrumMasterId", "form.field.error.null");
        }
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getProjectOwnerId() {
        return projectOwnerId;
    }

    public void setProjectOwnerId(Long projectOwnerId) {
        this.projectOwnerId = projectOwnerId;
    }

    public Long getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManagerId(Long projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    public Long getScrumMasterId() {
        return scrumMasterId;
    }

    public void setScrumMasterId(Long scrumMasterId) {
        this.scrumMasterId = scrumMasterId;
    }
}
