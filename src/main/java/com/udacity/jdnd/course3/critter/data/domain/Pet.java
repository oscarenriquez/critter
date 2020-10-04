package com.udacity.jdnd.course3.critter.data.domain;

import com.udacity.jdnd.course3.critter.pet.PetType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Pet {

    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 100)
    private String name;
    private LocalDate birthDate;
    @Column(length = 200)
    private String notes;
    private PetType petType;
    @OneToOne
    private Customer customer;

    public Pet(String name, LocalDate birthDate, String notes, PetType petType, Customer customer) {
        this.name = name;
        this.birthDate = birthDate;
        this.notes = notes;
        this.petType = petType;
        this.customer = customer;
    }

    public Pet() {
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
