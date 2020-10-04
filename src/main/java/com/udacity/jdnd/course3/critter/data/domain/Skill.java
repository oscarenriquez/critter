package com.udacity.jdnd.course3.critter.data.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Skill {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 100)
    private String name;

    public Skill(String name) {
        this.name = name;
    }

    public Skill() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
