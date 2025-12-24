package com.igrejaplus.repository;

import com.igrejaplus.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("""
            SELECT s FROM Schedule s
            LEFT JOIN FETCH s.items i
            LEFT JOIN FETCH i.member
            WHERE s.id = :id
            """)
    Optional<Schedule> findByIdWithItems(Long id);

    @Query("""
            SELECT DISTINCT s FROM Schedule s
            LEFT JOIN FETCH s.items i
            LEFT JOIN FETCH i.member
            """)
    List<Schedule> findAllWithItems();
}
