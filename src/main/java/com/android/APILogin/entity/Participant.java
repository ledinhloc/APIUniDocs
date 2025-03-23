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
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "conId")
    Conversation conversation;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime joinAt;

    @Column(nullable = false)
    LocalDateTime leftAt;
}
