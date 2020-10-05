package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.data.domain.Employee;
import com.udacity.jdnd.course3.critter.data.domain.Pet;
import com.udacity.jdnd.course3.critter.data.domain.Schedule;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.UserService;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final UserService userService;
    private final PetService petService;
    private static final MapperFactory mapperFactory;

    static {
        mapperFactory = new DefaultMapperFactory.Builder().build();
    }

    public ScheduleController(ScheduleService scheduleService, UserService userService, PetService petService) {
        this.scheduleService = scheduleService;
        this.userService = userService;
        this.petService = petService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        addEmployees(scheduleDTO.getEmployeeIds(), schedule);
        addPets(scheduleDTO.getPetIds(), schedule);

        schedule = scheduleService.saveSchedule(schedule);

        return convertSchedule(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.findAllSchedules().stream().map(ScheduleController::convertSchedule)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return scheduleService.findSchedulesByPetId(petId).stream().map(ScheduleController::convertSchedule)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.findSchedulesByEmployeeId(employeeId).stream().map(ScheduleController::convertSchedule)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.findSchedulesByCustomerId(customerId).stream().map(ScheduleController::convertSchedule)
                .collect(Collectors.toList());
    }

    private static ScheduleDTO convertSchedule(Schedule schedule) {
//        mapperFactory.classMap(Schedule.class, ScheduleDTO.class).customize(new CustomMapper<Schedule, ScheduleDTO>() {
//            @Override
//            public void mapAtoB(Schedule schedule, ScheduleDTO scheduleDTO, MappingContext context) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(scheduleDTO.getActivities());
        scheduleDTO.setId(schedule.getId());
                if( !CollectionUtils.isEmpty( schedule.getEmployees() ) ){
                    scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId)
                            .collect(Collectors.toList()));
                }
                if( !CollectionUtils.isEmpty( schedule.getPets() ) ){
                    scheduleDTO.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
                }
                if( !CollectionUtils.isEmpty( schedule.getActivities() ) ){
                    scheduleDTO.setActivities(schedule.getActivities());
                }
//            }
//        }).byDefault().register();
//
//        MapperFacade mapperFacade = mapperFactory.getMapperFacade();
//        return mapperFacade.map(schedule, ScheduleDTO.class);
        return scheduleDTO;
    }

    private void addEmployees(List<Long> employeeIds, Schedule schedule) {
        if(employeeIds == null || employeeIds.size() == 0 || schedule == null){
            return;
        }
        employeeIds.forEach(employeeId -> {
            Employee employee = userService.findEmployeeById(employeeId);
            schedule.addEmployee(employee);
        });
    }

    private void addPets(List<Long> petIds, Schedule schedule) {
        if(petIds == null || petIds.size() == 0 || schedule == null){
            return;
        }
        petIds.forEach(petId -> {
            Pet pet = petService.findPetById(petId);
            schedule.addPet(pet);
        });
    }
}
