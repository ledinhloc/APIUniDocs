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
    Long conId;

    @Column(nullable = false)
    String conName;

    @Column(nullable = false)
    Boolean isGroup;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    // Hình đại diện group
    String image;

    // Hình nền group
    String backgroundUrl;

    // Màu nền
    String themeColor;

    @Column(nullable = false)
    @Min(2)
    Integer numMember;

    @JsonIgnore
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    List<Participant> participants;

    @JsonIgnore
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
    List<ChatLine> chatLines;
}
