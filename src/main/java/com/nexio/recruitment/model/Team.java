package com.nexio.recruitment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Team {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String teamName;

    String projectName;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "employees_teams", joinColumns = @JoinColumn(name = "team_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
    List<Employee> employees = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "po_id", insertable = false, updatable = false)
    Employee projectOwner;
    @Column(nullable = true,  name = "po_id")
    Long projectOwnerId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "pm_id", insertable = false, updatable = false)
    Employee projectManager;
    @Column(nullable = true,  name = "pm_id")
    Long projectManagerId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "scrum_master_id", insertable = false, updatable = false)
    Employee scrumMaster;
    @Column(nullable = true, name = "scrum_master_id")
    Long scrumMasterId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Employee getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(Employee projectOwner) {
        this.projectOwner = projectOwner;
    }

    public Long getProjectOwnerId() {
        return projectOwnerId;
    }

    public void setProjectOwnerId(Long projectOwnerId) {
        this.projectOwnerId = projectOwnerId;
    }

    public Employee getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(Employee projectManager) {
        this.projectManager = projectManager;
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



    public Employee getScrumMaster() {
        return scrumMaster;
    }

    public void setScrumMaster(Employee scrumMaster) {
        this.scrumMaster = scrumMaster;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return  teamName;

    }
}
