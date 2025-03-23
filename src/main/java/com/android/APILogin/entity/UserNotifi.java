package com.android.APILogin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name="userNotifi",
        uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "notiId"})
)
public class UserNotifi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="userId")
    User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "notiId")
    Notification notification;

    @Column(nullable = false)
    Boolean isRead;

}
