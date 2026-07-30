package com.marketing.analytics.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "uploaded_datasets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadedDataset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long datasetId;

    @Column(nullable = false, length = 255)
    private String fileName;

    @Column(name = "upload_date", nullable = false, updatable = false)
    private LocalDateTime uploadDate;

    @ManyToOne
    @JoinColumn(name = "uploaded_by", nullable = false)
    private User uploadedBy;

    @Column(nullable = false)
    private Integer totalRecords;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DatasetStatus status;

    @Column(name = "file_path", length = 500)
    private String filePath;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @PrePersist
    protected void onCreate() {
        uploadDate = LocalDateTime.now();
    }

    public enum DatasetStatus {
        PENDING,
        VALIDATING,
        VALIDATED,
        PREPROCESSING,
        PREPROCESSED,
        FAILED
    }
}
