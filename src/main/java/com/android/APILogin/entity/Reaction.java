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
    Long reaction_id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="chatline_id")
    ChatLine chatline;

    @Enumerated(EnumType.STRING)
    IconType icon_type;

}
