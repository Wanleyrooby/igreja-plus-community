package com.igrejaplus.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "schedule_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String ministryRole; // exemplo: "voz", "baixo", "pregador"
}
