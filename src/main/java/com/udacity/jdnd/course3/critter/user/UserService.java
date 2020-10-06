package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.data.domain.Customer;
import com.udacity.jdnd.course3.critter.data.domain.Employee;
import com.udacity.jdnd.course3.critter.data.domain.Pet;
import com.udacity.jdnd.course3.critter.data.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.data.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.data.repository.PetRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final PetRepository petRepository;

    public UserService(CustomerRepository customerRepository, EmployeeRepository employeeRepository,
                       PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
    }

    public Customer saveCustomer(Customer customer) {
        return this.customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = this.customerRepository.findAll();
        return customers.stream().peek(customer -> customer.setPets(petRepository.findPetsByCustomer(customer))).collect(Collectors.toList());
    }

    public Customer findCustomerByPet(Pet pet) {
        Customer customer = this.customerRepository.findCustomerByPets(pet);
        customer.setPets(petRepository.findPetsByCustomer(customer));
        return customer;
    }

    public Employee saveEmployee(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    public Employee findEmployeeById(long employeeId) {
        return this.employeeRepository.findById(employeeId).orElse(null);
    }

    public List<Employee> getAllEmployees() {
        return this.employeeRepository.findAll();
    }

    public List<Employee> findEmployeesBySkillsAndDayAvailable(final Set<EmployeeSkill> skills, DayOfWeek dayAvailable) {
        return employeeRepository.findEmployeesByDaysAvailableAndSkillsIn(dayAvailable, skills);
    }

    public Customer findCustomerById(Long customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }

}
