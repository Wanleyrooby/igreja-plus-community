package com.igrejaplus.mapper;

import com.igrejaplus.dto.comment.CommentRequest;
import com.igrejaplus.dto.comment.CommentResponse;
import com.igrejaplus.model.Comment;
import com.igrejaplus.model.Event;
import com.igrejaplus.model.Member;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CommentMapper {

    public CommentResponse toResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getApproved(),
                comment.getMember().getId(),
                comment.getMember().getName(),
                comment.getEvent().getId(),
                comment.getCreatedAt()
        );
    }

    public Comment toEntity(CommentRequest dto, Member member, Event event) {
        return Comment.builder()
                .content(dto.content())
                .member(member)
                .event(event)
                .approved(false)
                .createdAt(Instant.now())
                .build();
    }
}
