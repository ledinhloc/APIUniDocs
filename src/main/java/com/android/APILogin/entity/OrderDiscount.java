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
@Table(name="orderDiscount",
        uniqueConstraints = @UniqueConstraint(columnNames = {"order_id", "discount_id"}))
public class OrderDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="orderId")
    private Order order;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="discountId")
    private Discount discount;

    @Column(nullable = false)
    @Min(1)
    private Double discountAmount;
}
