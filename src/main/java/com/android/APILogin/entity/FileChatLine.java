package com.android.APILogin.entity;

import com.android.APILogin.enums.FileType;
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
@Table(name="fileChatline")
public class FileChatLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long fileId;

    @Column(nullable = false)
    String fileUrl;

    @Enumerated(EnumType.STRING)
    FileType fileType;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="chatlineId")
    ChatLine chatline;
}
