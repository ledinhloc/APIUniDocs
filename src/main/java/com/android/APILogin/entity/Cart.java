package com.android.APILogin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long cartId;

    @Column(nullable = false)
    @Min(1)
    Integer quantity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "doc_id")
    Document document;
}
