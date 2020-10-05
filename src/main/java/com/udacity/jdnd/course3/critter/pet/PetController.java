package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.data.domain.Customer;
import com.udacity.jdnd.course3.critter.data.domain.Pet;
import com.udacity.jdnd.course3.critter.user.UserService;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
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
    private static final MapperFactory mapperFactory;

    static {
        mapperFactory = new DefaultMapperFactory.Builder().build();
    }

    public PetController(PetService petService, UserService userService) {
        this.petService = petService;
        this.userService = userService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
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
        mapperFactory.classMap(Pet.class, PetDTO.class).customize(new CustomMapper<Pet, PetDTO>() {
            @Override
            public void mapAtoB(Pet pet, PetDTO petDTO, MappingContext context) {
                petDTO.setOwnerId(pet.getCustomer().getId());
            }
        }).byDefault().register();

        MapperFacade mapperFacade = mapperFactory.getMapperFacade();
        return mapperFacade.map(pet, PetDTO.class);
    }
}
