package com.udacity.jdnd.course3.critter.data.domain;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

@Entity
public class Schedule {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDate date;
    @ManyToMany
    @JoinTable(
            name = "schedule_employee",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employees;
    @ManyToMany
    @JoinTable(
            name = "schedule_pet",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id"))
    private List<Pet> pets;
    @ManyToMany
    @JoinTable(
            name = "schedule_activity",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> activities;

    public Schedule(LocalDate date) {
        this.date = date;
    }

    public Schedule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public Set<Skill> getActivities() {
        return activities;
    }

    public void setActivities(Set<Skill> activities) {
        this.activities = activities;
    }

    public void addEmployee(Employee employee) {
        if(employees == null) {
            employees = new ArrayList<>();
        }
        employees.add(employee);
    }

    public void addPet(Pet pet) {
        if(pets == null) {
            pets = new ArrayList<>();
        }
        pets.add(pet);
    }

    public void addActivity(Skill skill) {
        if(activities == null) {
            activities = new HashSet<>();
        }
        activities.add(skill);
    }
}
