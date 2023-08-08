package com.looment.uploadservice.services;

import com.looment.uploadservice.dtos.UploadRequest;
import com.looment.uploadservice.dtos.UploadResponse;

public interface IUploadService {
//    UploadResponse uploadFileS3(UploadRequest uploadRequest);
    UploadResponse uploadFileFirebase(UploadRequest uploadRequest);
}
