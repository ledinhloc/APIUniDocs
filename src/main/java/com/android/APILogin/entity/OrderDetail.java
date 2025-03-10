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
@Table(name="order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="order_id")
    Order order;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="doc_id")
    Document document;

    @Column(nullable = false)
    @Min(1)
    Integer quantity;

    @Column(nullable = false, scale = 2)
    Double original_price;

    @Column(nullable = false, scale = 2)
    Double sell_price;
}
