package com.jeanbarcellos.project106.domain;

import java.util.ArrayList;
import java.util.List;

import com.jeanbarcellos.core.domain.IAggregateRoot;
import com.jeanbarcellos.core.domain.IEntity;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Entity
@Table(name = "category")
public class Category implements IEntity, IAggregateRoot {

    @Id
    @GeneratedValue(generator = "category_id_seq_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "category_id_seq_generator", sequenceName = "category_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    protected String description;

    // -----

    @Builder.Default
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

}
