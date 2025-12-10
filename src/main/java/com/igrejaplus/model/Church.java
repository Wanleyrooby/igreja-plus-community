package com.igrejaplus.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "churches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Church {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String slug;

    private String plan;

    private Instant createdAt;

    @OneToMany(mappedBy = "church")
    private List<User> users;
}
