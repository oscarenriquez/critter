package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.data.domain.Employee;
import com.udacity.jdnd.course3.critter.data.domain.Pet;
import com.udacity.jdnd.course3.critter.data.domain.Schedule;
import com.udacity.jdnd.course3.critter.data.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.data.repository.PetRepository;
import com.udacity.jdnd.course3.critter.data.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;
    private final PetRepository petRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, EmployeeRepository employeeRepository, PetRepository petRepository) {
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
    }

    Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    List<Schedule> findAllSchedules() {
        return scheduleRepository.findAll();
    }

    List<Schedule> findSchedulesByPetId(Long petId) {
        Pet pet = petRepository.findById(petId).orElse(null);
        return scheduleRepository.findSchedulesByPets(pet);
    }

    List<Schedule> findSchedulesByEmployeeId(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        return scheduleRepository.findSchedulesByEmployees(employee);
    }

    List<Schedule> findSchedulesByCustomerId(long customerId) {
        return scheduleRepository.findSchedulesByCustomer(customerId);
    }

}
