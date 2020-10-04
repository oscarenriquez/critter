package com.udacity.jdnd.course3.critter.data.domain;

import javax.persistence.*;

import java.util.Set;
import java.util.HashSet;

@Entity
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 100)
    private String name;
    @ManyToMany
    @JoinTable(
            name = "employee_skill",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> skills;

    @ManyToMany
    @JoinTable(
            name = "employee_available",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "dayOfWeek_id"))
    private Set<DayOfWeek> daysAvailable;

    public Employee(String name) {
        this.name = name;
    }

    public Employee() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public void addSkill(Skill skill) {
        if(skills == null) {
            skills = new HashSet<>();
        }
        skills.add(skill);
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public void addDayAvailable(DayOfWeek dayOfWeek) {
        if(daysAvailable == null) {
            daysAvailable = new HashSet<>();
        }
        daysAvailable.add(dayOfWeek);
    }
}
