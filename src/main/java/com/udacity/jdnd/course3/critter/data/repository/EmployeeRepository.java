package com.udacity.jdnd.course3.critter.data.repository;

import com.udacity.jdnd.course3.critter.data.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
