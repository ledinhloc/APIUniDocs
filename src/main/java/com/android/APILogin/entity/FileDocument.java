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
public class FileDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long file_id;

    @Column(nullable = false)
    String file_url;

    @Enumerated(EnumType.STRING)
    FileType file_type;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name= "doc_id")
    Document document;
}
