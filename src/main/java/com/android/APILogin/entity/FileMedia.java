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
    String file_url;

    @Enumerated(EnumType.STRING)
    FileType file_type;

    //many to one
    @JsonIgnore
    @OneToMany(mappedBy = "fileMedia", cascade = CascadeType.ALL)
    List<Review> reviews;
}
