package com.android.APILogin.entity;

import com.android.APILogin.enums.ChatStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Table(name="chatLine")
public class ChatLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long chatlineId;

    Long chatlineParentId;

    @Column(nullable = false)
    String content;

    @Enumerated(EnumType.STRING)
    ChatStatus chatStatus;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime sendAt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "conId")
    Conversation conversation;

    @JsonIgnore
    @OneToMany(mappedBy = "chatline", cascade = CascadeType.ALL)
    List<FileChatLine> fileChatLines;

    @JsonIgnore
    @OneToMany(mappedBy = "chatline", cascade = CascadeType.ALL)
    List<Seen> seens;
}
