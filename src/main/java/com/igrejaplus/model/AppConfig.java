package com.igrejaplus.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "app_config")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppConfig {

    @Id
    private Long id;

    @Column(nullable = false)
    private String churchName;

    private String address;

    private String pastorName;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = 1L;
        }
    }
}
