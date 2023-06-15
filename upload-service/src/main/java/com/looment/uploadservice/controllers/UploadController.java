package com.looment.uploadservice.controllers;

import com.looment.uploadservice.dtos.BaseResponse;
import com.looment.uploadservice.dtos.UploadRequest;
import com.looment.uploadservice.dtos.UploadResponse;
import com.looment.uploadservice.services.UploadService;
import com.looment.uploadservice.utils.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/upload")
@RequiredArgsConstructor
public class UploadController extends BaseController {
    private final UploadService uploadService;

    @PostMapping("firebase")
    public ResponseEntity<BaseResponse> uploadFirebase(@ModelAttribute UploadRequest uploadRequest) {
        UploadResponse uploadResponse =  uploadService.uploadFileFirebase(uploadRequest);
        return responseCreated("Successfully upload a File to Firebase", uploadResponse);
    }

    @PostMapping("s3")
    public ResponseEntity<BaseResponse> uploadS3(@ModelAttribute UploadRequest uploadRequest) {
        UploadResponse uploadResponse =  uploadService.uploadFileS3(uploadRequest);
        return responseCreated("Successfully upload a File to AWS S3", uploadResponse);
    }
}
