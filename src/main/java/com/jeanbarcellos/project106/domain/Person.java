package com.jeanbarcellos.project106.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import com.jeanbarcellos.core.domain.IAggregateRoot;
import com.jeanbarcellos.core.domain.IEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Entity
@Table(name = "person")
@DynamicUpdate
public class Person implements IEntity, IAggregateRoot {

    public static final String FIELD_CPF = "personalNumber";

    @Id
    @GeneratedValue(generator = "person_id_seq_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "person_id_seq_generator", sequenceName = "person_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "personal_number", nullable = false)
    private String personalNumber;

    @Column(name = "date_birthday", nullable = false)
    private LocalDate dateBirthday;

    @Column(name = "email", nullable = false)
    private String email;

    // -----

    @Builder.Default
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

}
