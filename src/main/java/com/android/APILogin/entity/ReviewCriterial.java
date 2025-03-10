package com.android.APILogin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name="review_criterial")
public class ReviewCriterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long review_criterial_id;

    //
    @Column(nullable = false)
    String rating;

    @Column(unique = true)
    String name;

    @JsonIgnore
    @OneToMany(mappedBy = "reviewCriterial", cascade = CascadeType.ALL)
    List<Review> reviews;
}
