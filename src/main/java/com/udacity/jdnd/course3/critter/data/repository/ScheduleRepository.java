package com.udacity.jdnd.course3.critter.data.repository;

import com.udacity.jdnd.course3.critter.data.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}