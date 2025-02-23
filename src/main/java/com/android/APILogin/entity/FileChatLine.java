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
@Table(name="file_chatline")
public class FileChatLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long file_id;

    @Column(nullable = false)
    private String file_url;

    @Enumerated(EnumType.STRING)
    private FileType file_type;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="chatline_id")
    private ChatLine chatline;
}
