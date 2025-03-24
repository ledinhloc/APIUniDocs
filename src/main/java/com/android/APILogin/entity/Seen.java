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
        uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "chatlineId"})
)
public class Seen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long seenId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="userId")
    User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="chatLineId")
    ChatLine chatLine;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime seenAt;
}
