package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.data.domain.Customer;
import com.udacity.jdnd.course3.critter.data.domain.Pet;
import com.udacity.jdnd.course3.critter.data.repository.PetRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet findPetById(Long petId) {
       return petRepository.findById(petId).orElse(null);
    }

    public List<Pet> findAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> findPetsByCustomerId(Customer customer) {
        return petRepository.findPetsByCustomer(customer);
    }
}
