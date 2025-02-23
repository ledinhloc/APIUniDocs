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
@Table(name="file_media")
public class FileMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long file_id;

    @Column(nullable = false)
    private String file_url;

    @Enumerated(EnumType.STRING)
    private FileType file_type;

    @JsonIgnore
    @OneToMany(mappedBy = "fileMedia", cascade = CascadeType.ALL)
    private List<Review> reviews;
}
