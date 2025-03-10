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
@Table(name="chat_line")
public class ChatLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long chatline_id;

    Long chatline_parent_id;

    @Column(nullable = false)
    String content;

    @Enumerated(EnumType.STRING)
    ChatStatus chat_status;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime send_at;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "con_id")
    Conversation conversation;

    @JsonIgnore
    @OneToMany(mappedBy = "chatline", cascade = CascadeType.ALL)
    List<FileChatLine> fileChatLines;

    @JsonIgnore
    @OneToMany(mappedBy = "chatline", cascade = CascadeType.ALL)
    List<Seen> seens;
}
