package com.nexio.recruitment.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nexio.recruitment.model.competence.*;
import com.nexio.recruitment.model.competence.Interface.Competences;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    String firstName;

    @NotNull
    String lastName;

    @NotNull
    @Enumerated(EnumType.STRING)
    Position position;

    @Transient
    Competences competences;

    @Min(0)
    Float salary;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="supervisor_id", updatable = false, insertable = false)
    Employee supervisor;
    @Column(nullable = true, updatable = true, name = "supervisor_id")
    Long supervisorId;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "emoloyess_teams", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "team_id"))
    List<Team> teams = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {

        this.position = position;
    }

    public Competences getCompetences() {
        return competences;
    }

    public void setCompetences(Competences competences) {
        this.competences = competences;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public Employee getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Employee supervisor) {
        this.supervisor = supervisor;
    }

    public Long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Long supervisorId) {
        this.supervisorId = supervisorId;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public void removeTeam(Team team) {
        this.teams.remove(team);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @PostLoad
    public void postLoad(){

        switch (this.position){
            case PM:
                this.competences = new ProjectManager();
                break;
            case PO:
                this.competences = new ProjectOwner();
                break;
            case SCRUMASTER:
                this.competences = new ScrumMaster();
                break;
            case DEVOPS:
                this.competences = new Devops();
                break;
            case TESTER:
                this.competences = new Tester();
                break;
            case ANALYST:
                this.competences = new Analyst();
                break;
            case DEVELOPER:
                competences = new Developer();
                break;
        }
    }

    public enum Position{
        PO, PM, SCRUMASTER, DEVOPS, DEVELOPER, TESTER, ANALYST
    }

}
