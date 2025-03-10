package com.android.APILogin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

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
    String con_name;

    @Column(nullable = false)
    Boolean is_group;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime created_at;

    // Hình đại diện group
    String image;

    // Hình nền group
    String background_url;

    // Màu nền
    String theme_color;

    @Column(nullable = false)
    @Min(2)
    Integer num_member;

    @JsonIgnore
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    List<Participant> participants;

    @JsonIgnore
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    List<ChatLine> chatLines;
}
