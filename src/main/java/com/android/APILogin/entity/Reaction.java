package com.android.APILogin.entity;

import com.android.APILogin.enums.IconType;
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
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long reactionId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="userId")
    User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="chatlineId")
    ChatLine chatline;

    @Enumerated(EnumType.STRING)
    IconType iconType;

}
