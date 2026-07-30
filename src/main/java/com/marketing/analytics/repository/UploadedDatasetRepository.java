package com.marketing.analytics.repository;

import com.marketing.analytics.entity.UploadedDataset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadedDatasetRepository extends JpaRepository<UploadedDataset, Long> {
    List<UploadedDataset> findByStatusOrderByUploadDateDesc(UploadedDataset.DatasetStatus status);
}
