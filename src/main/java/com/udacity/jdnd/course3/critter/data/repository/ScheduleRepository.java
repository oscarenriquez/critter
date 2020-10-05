package com.udacity.jdnd.course3.critter.data.repository;

import com.udacity.jdnd.course3.critter.data.domain.Employee;
import com.udacity.jdnd.course3.critter.data.domain.Pet;
import com.udacity.jdnd.course3.critter.data.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findSchedulesByPets(Pet pet);

    List<Schedule> findSchedulesByEmployees(Employee employee);

    @Query("select s from Schedule s inner join s.pets p where p.customer.id = :customerId ")
    List<Schedule> findSchedulesByCustomer(long customerId);
}
