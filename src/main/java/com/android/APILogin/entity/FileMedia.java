package com.android.APILogin.entity;

import com.android.APILogin.enums.FileType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name="fileMedia")
public class FileMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long fileId;

    @Column(nullable = false)
    String fileUrl;

    @Enumerated(EnumType.STRING)
    FileType fileType;

    //many to one
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "reviewId")
    Review review;
}
