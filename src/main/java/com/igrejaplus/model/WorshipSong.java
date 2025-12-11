package com.igrejaplus.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "worship_songs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorshipSong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 150)
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Size(min = 3, max = 100)
    @Column(nullable = false)
    private String author;

    @Size(max = 10)
    private String tone;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String lyrics;

    private Integer duration; // Em segundos

    @ElementCollection
    @CollectionTable(
            name = "worship_song_tags",
            joinColumns = @JoinColumn(name = "worship_song_id")
    )
    @Column(name = "tag")
    private List<String> tags;


    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "church_id", nullable = false)
    private Church church;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }
}
