package com.looment.uploadservice.repositories;

import com.looment.uploadservice.entities.Uploads;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UploadRepository extends JpaRepository<Uploads, UUID> {
}
