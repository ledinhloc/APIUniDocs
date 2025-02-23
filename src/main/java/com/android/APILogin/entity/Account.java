package com.android.APILogin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long account_id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDateTime update_at;

    @Column(nullable = false)
    private LocalDateTime created_at;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Boolean is_active;

    @JsonIgnore
    @OneToOne(mappedBy = "account")
    private User user;
}
