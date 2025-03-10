package com.android.APILogin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(
        name = "seen",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "chatline_id"})
)
public class Seen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long seen_id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="chatline_id")
    ChatLine chatline;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime seen_at;
}
