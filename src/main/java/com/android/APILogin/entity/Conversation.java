package com.android.APILogin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long con_id;

    @Column(nullable = false)
    private String con_name;

    @Column(nullable = false)
    private Boolean is_group;

    @Column(nullable = false)
    private LocalDateTime created_at;

    // Hình đại diện group
    private String image;

    // Hình nền group
    private String background_url;

    // Màu nền
    private String theme_color;

    @Column(nullable = false)
    @Min(2)
    private Integer num_member;

    @JsonIgnore
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    private List<Participant> participants;

    @JsonIgnore
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    private List<ChatLine> chatLines;
}
