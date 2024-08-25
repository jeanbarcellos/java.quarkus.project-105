package com.jeanbarcellos.project106.domain;

import org.hibernate.annotations.DynamicUpdate;

import com.jeanbarcellos.core.domain.IEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@DynamicUpdate
@Table(name = "comment")
public class Comment implements IEntity {

    @Id
    @GeneratedValue(generator = "comment_id_seq_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "comment_id_seq_generator", sequenceName = "comment_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "comment_post_id_fk"), nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "comment_author_id_fk"), nullable = false)
    private Person author;

    @Column(name = "text", nullable = false)
    private String text;

}
