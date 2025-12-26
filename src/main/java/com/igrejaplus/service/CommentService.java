package com.igrejaplus.service;

import com.igrejaplus.dto.comment.CommentRequest;
import com.igrejaplus.dto.comment.CommentResponse;
import com.igrejaplus.exception.ResourceNotFoundException;
import com.igrejaplus.mapper.CommentMapper;
import com.igrejaplus.model.Comment;
import com.igrejaplus.model.Event;
import com.igrejaplus.model.Member;
import com.igrejaplus.repository.CommentRepository;
import com.igrejaplus.repository.EventRepository;
import com.igrejaplus.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final EventRepository eventRepository;
    private final CommentMapper commentMapper;

    public CommentResponse create(CommentRequest dto) {

        Member member = memberRepository.findById(dto.memberId())
                .orElseThrow(() -> new ResourceNotFoundException("Membro não encontrado."));

        Event event = eventRepository.findById(dto.eventId())
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado."));

        Comment comment = commentMapper.toEntity(dto, member, event);
        Comment saved = commentRepository.save(comment);

        return commentMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> findApprovedByEvent(Long eventId) {

        return commentRepository.findByEventIdAndApprovedTrue(eventId)
                .stream()
                .map(commentMapper::toResponse)
                .toList();
    }

    // ADMIN
    public CommentResponse approve(Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentário não encontrado."));

        comment.setApproved(true);
        return commentMapper.toResponse(comment);
    }

    // ADMIN
    public void delete(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new ResourceNotFoundException("Comentário não encontrado.");
        }
        commentRepository.deleteById(commentId);
    }

}
