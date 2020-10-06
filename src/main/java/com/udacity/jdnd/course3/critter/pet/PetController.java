package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.data.domain.Customer;
import com.udacity.jdnd.course3.critter.data.domain.Pet;
import com.udacity.jdnd.course3.critter.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;
    private final UserService userService;

    public PetController(PetService petService, UserService userService) {
        this.petService = petService;
        this.userService = userService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setPetType(petDTO.getType());
        Customer customer = userService.findCustomerById(petDTO.getOwnerId());
        if (customer != null) {
            pet.setCustomer(customer);
            return convertPet(petService.savePet(pet));
        }
        return null;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertPet(petService.findPetById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return petService.findAllPets().stream().map(PetController::convertPet).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        Customer customer = userService.findCustomerById(ownerId);
        return petService.findPetsByCustomerId(customer).stream().map(PetController::convertPet)
                .collect(Collectors.toList());
    }

    private static PetDTO convertPet(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setName(pet.getName());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setNotes(pet.getNotes());
        petDTO.setType(pet.getPetType());
        if (pet.getCustomer() != null) {
            petDTO.setOwnerId(pet.getCustomer().getId());
        }
        return petDTO;
    }
}
