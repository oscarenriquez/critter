package com.udacity.jdnd.course3.critter.user;


import com.udacity.jdnd.course3.critter.data.domain.*;
import com.udacity.jdnd.course3.critter.pet.PetService;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PetService petService;
    private static final MapperFactory mapperFactory;

    static {
        mapperFactory = new DefaultMapperFactory.Builder().build();
    }

    public UserController(UserService userService, PetService petService) {
        this.userService = userService;
        this.petService = petService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);

        addPets(customerDTO.getPetIds(), customer);

        return convertCustomer(userService.saveCustomer(customer));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return userService.getAllCustomers().stream()
                .map(UserController::convertCustomer).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petService.findPetById(petId);
        return convertCustomer(userService.findCustomerByPet(pet));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        addAvailability(employeeDTO.getDaysAvailable(), employee);

        return convertEmployee(userService.saveEmployee(employee));
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertEmployee(userService.findEmployeeById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = userService.findEmployeeById(employeeId);
        addAvailability(daysAvailable, employee);
        userService.saveEmployee(employee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        Set<EmployeeSkill> skills = employeeDTO.getSkills();
        List<Employee> employeeList = userService.findEmployeesBySkillsAndDayAvailable(
                skills,
                employeeDTO.getDate().getDayOfWeek());

        return employeeList.stream()
                .map(UserController::convertEmployee)
                .filter(employee -> {
                    Set<EmployeeSkill> employeeSkills = new HashSet<>(employee.getSkills());
                    employeeSkills.retainAll(skills);
                    return employeeSkills.size() == skills.size();
                })
                .collect(Collectors.toList());
    }

    private static CustomerDTO convertCustomer(Customer customer) {
        mapperFactory.classMap(Customer.class, CustomerDTO.class).customize(new CustomMapper<Customer, CustomerDTO>() {
            @Override
            public void mapAtoB(Customer customer, CustomerDTO customerDTO, MappingContext context) {
                if( !CollectionUtils.isEmpty( customer.getPets() ) ){
                    customerDTO.setPetIds(customer.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
                }
            }
        })
        .byDefault()
        .register();

        MapperFacade mapperFacade = mapperFactory.getMapperFacade();
        return mapperFacade.map(customer, CustomerDTO.class);
    }

    private static EmployeeDTO convertEmployee(Employee employee) {
        mapperFactory.classMap(Employee.class, EmployeeDTO.class).customize(new CustomMapper<Employee, EmployeeDTO>() {
            @Override
            public void mapAtoB(Employee employee, EmployeeDTO employeeDTO, MappingContext context) {
                if (!CollectionUtils.isEmpty(employee.getSkills())) {
                    employeeDTO.setSkills(employee.getSkills());
                }
            }
        })
        .byDefault()
        .register();

        MapperFacade mapperFacade = mapperFactory.getMapperFacade();
        return mapperFacade.map(employee, EmployeeDTO.class);
    }

    private void addPets(List<Long> petIds, Customer customer) {
        if(petIds == null || petIds.size() == 0 || customer == null){
            return;
        }
        petIds.forEach(petId -> {
            Pet pet = petService.findPetById(petId);
            customer.addPet(pet);
        });
    }

    private void addAvailability(Set<DayOfWeek> availability, Employee employee) {
        if(availability == null || availability.size() == 0 || employee == null){
            return;
        }
        availability.forEach(employee::addDayAvailable);
    }
}
