package com.udacity.jdnd.course3.critter.user;


import com.udacity.jdnd.course3.critter.data.domain.Customer;
import com.udacity.jdnd.course3.critter.data.domain.Employee;
import com.udacity.jdnd.course3.critter.data.domain.Skill;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
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

    private UserService userService;
    private MapperFactory mapperFactory;

    public UserController(UserService userService) {
        this.userService = userService;
        this.mapperFactory = new DefaultMapperFactory.Builder().build()
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return convertCustomer(userService.saveCustomer(customer));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return userService.getAllCustomers().stream()
                .map(customer -> convertCustomer(customer)).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return convertCustomer(userService.findCustomerByPet(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return convertEmployee(userService.saveEmployee(employee));
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertEmployee(userService.findEmployeeById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = userService.findEmployeeById(employeeId);
        daysAvailable.stream().forEach(dayOfWeek -> {
            com.udacity.jdnd.course3.critter.data.domain.DayOfWeek day =
                    new com.udacity.jdnd.course3.critter.data.domain.DayOfWeek();
            day.setName(dayOfWeek.name());
            employee.addDayAvailable(day);
        });
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        employeeDTO.getDate()

    }

    private CustomerDTO convertCustomer(Customer customer) {
        mapperFactory.classMap(Customer.class, CustomerDTO.class).customize(new CustomMapper<Customer, CustomerDTO>() {
            @Override
            public void mapAtoB(Customer customer, CustomerDTO customerDTO, MappingContext context) {
                customerDTO.setPetIds(customer.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList()));
            }
        })
        .byDefault()
        .register();

        MapperFacade mapperFacade = mapperFactory.getMapperFacade();
        return mapperFacade.map(customer, CustomerDTO.class);
    }

    private EmployeeDTO convertEmployee(Employee employee) {
        mapperFactory.classMap(Employee.class, EmployeeDTO.class).customize(new CustomMapper<Employee, EmployeeDTO>() {
            @Override
            public void mapAtoB(Employee employee, EmployeeDTO employeeDTO, MappingContext context) {
                employeeDTO.setSkills(
                        employee.getSkills().stream().map(skill -> EmployeeSkill.valueOf(skill.getName()))
                                .collect(Collectors.toSet())
                );
                employeeDTO.setDaysAvailable(
                        employee.getDaysAvailable().stream().map(dayOfWeek -> DayOfWeek.valueOf(dayOfWeek.getName()))
                                .collect(Collectors.toSet())
                );
            }
        })
        .byDefault()
        .register();

        MapperFacade mapperFacade = mapperFactory.getMapperFacade();
        return mapperFacade.map(employee, EmployeeDTO.class);
    }

}
