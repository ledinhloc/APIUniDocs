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
@Table(name="orderDetail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="orderId")
    Order order;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="docId")
    Document document;

    @Column(nullable = false)
    @Min(1)
    Integer quantity;

    @Column(nullable = false, scale = 2)
    Double originalPrice;

    @Column(nullable = false, scale = 2)
    Double sellPrice;
}
