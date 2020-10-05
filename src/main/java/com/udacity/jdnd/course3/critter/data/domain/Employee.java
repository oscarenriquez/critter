package com.udacity.jdnd.course3.critter.data.domain;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;

import java.time.DayOfWeek;
import java.util.Set;
import java.util.HashSet;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String name;
    @ElementCollection(targetClass = EmployeeSkill.class)
    @JoinTable(name = "employee_skill", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "skill_name", nullable = false)
    private Set<EmployeeSkill> skills;

    @ElementCollection(targetClass = DayOfWeek.class)
    @JoinTable(name = "employee_available", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "dayOfWeek_id", nullable = false)
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

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public void addSkill(EmployeeSkill skill) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
