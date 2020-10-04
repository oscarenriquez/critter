package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.data.domain.Customer;
import com.udacity.jdnd.course3.critter.data.domain.Employee;
import com.udacity.jdnd.course3.critter.data.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.data.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private CustomerRepository customerRepository;
    private EmployeeRepository employeeRepository;

    public UserService(CustomerRepository customerRepository, EmployeeRepository employeeRepository) {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
    }

    public Customer saveCustomer(Customer customer) {
        return this.customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    public Customer findCustomerByPet(Long petId) {
        return this.customerRepository.findCustomerByPet(petId);
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

}
