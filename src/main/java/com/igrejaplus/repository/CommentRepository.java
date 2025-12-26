package com.igrejaplus.repository;

import com.igrejaplus.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByEventIdAndApprovedTrue(Long eventId);
}
