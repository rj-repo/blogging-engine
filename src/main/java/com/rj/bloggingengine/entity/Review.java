package com.rj.bloggingengine.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "review", uniqueConstraints = {@UniqueConstraint(columnNames = {"author"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "rating")
    private int rating;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Review review = (Review) o;
        return getId() != null && Objects.equals(getId(), review.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
