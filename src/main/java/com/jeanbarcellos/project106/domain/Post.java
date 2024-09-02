package com.jeanbarcellos.project106.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.DynamicUpdate;

import com.jeanbarcellos.core.domain.IAggregateRoot;
import com.jeanbarcellos.core.domain.IEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
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
@Table(name = "post")
public class Post implements IEntity, IAggregateRoot {

    @Id
    @GeneratedValue(generator = "post_id_seq_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "post_id_seq_generator", sequenceName = "post_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "post_category_id_fk"), nullable = false)
    private Category category;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "post_author_id_fk"), nullable = false)
    private Person author;

    // ---

    @Builder.Default
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @OrderBy("id DESC")
    private List<Comment> comments = new ArrayList<>();

    public Comment findCommentById(Long commentId) {
        return this.comments.stream()
                .filter(comment -> Objects.equals(comment.getId(), commentId))
                .findFirst()
                .orElse(null);
    }

}
