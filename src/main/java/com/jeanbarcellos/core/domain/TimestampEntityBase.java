package com.jeanbarcellos.core.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class TimestampEntityBase {

    @Setter(value = AccessLevel.PRIVATE)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Setter(value = AccessLevel.PRIVATE)
    @Column(name = "updated_at", nullable = false, updatable = true)
    private LocalDateTime updatedAt;

    @PrePersist
    private void onPrePersist() {
        var dateTime = LocalDateTime.now();
        this.setCreatedAt(dateTime);
        this.setUpdatedAt(dateTime);
    }

    @PreUpdate
    private void onPreUpdate() {
        this.setUpdatedAt(LocalDateTime.now());
    }
}