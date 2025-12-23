package com.igrejaplus.repository;

import com.igrejaplus.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findByStartAtAfter(LocalDateTime date, Pageable pageable);

}
