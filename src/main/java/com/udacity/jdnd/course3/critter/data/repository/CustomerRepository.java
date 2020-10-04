package com.udacity.jdnd.course3.critter.data.repository;

import com.udacity.jdnd.course3.critter.data.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findCustomerByPet(Long petId);
}
