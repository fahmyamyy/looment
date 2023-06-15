package com.looment.uploadservice.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.looment.uploadservice.dtos.UploadRequest;
import com.looment.uploadservice.dtos.UploadResponse;
import com.looment.uploadservice.entities.Uploads;
import com.looment.uploadservice.repositories.UploadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadService implements IUploadService {
    private final UploadRepository uploadRepository;
    private final ModelMapper modelMapper;
    private final AmazonS3 amazonS3;
    private final Storage storage;

    @Value("${aws.s3.bucketname}")
    private String bucketS3;
    @Value("${aws.s3.bucketname}")
    private String bucketFirebase;

    @Override
    public UploadResponse uploadFileS3(UploadRequest uploadRequest) {
        String fileName = uploadRequest.getFileName();
        String url = uploadS3(bucketS3, fileName, uploadRequest.getFile());

        return getUploadResponse(uploadRequest, url);
    }

    @Override
    public UploadResponse uploadFileFirebase(UploadRequest uploadRequest) {
        String url = uploadFirebase(uploadRequest.getFile());

        return getUploadResponse(uploadRequest, url);
    }

    private UploadResponse getUploadResponse(UploadRequest uploadRequest, String url) {
        Uploads uploads = new Uploads();
        uploads.setFileName(uploadRequest.getFileName());
        uploads.setUrl(url);
        uploads.setType(uploadRequest.getType());
        uploads.setUploadedBy(uploadRequest.getUploadedBy());
        uploadRepository.save(uploads);

        return modelMapper.map(uploads, UploadResponse.class);
    }

    private String uploadS3(String bucketName, String objectName, MultipartFile file) {

        try {
            File tempFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
            FileOutputStream os = new FileOutputStream(tempFile);
            os.write(file.getBytes());
            amazonS3.putObject(
                    new PutObjectRequest(bucketName, objectName, tempFile)
                            .withCannedAcl(CannedAccessControlList.PublicRead)
            );
            os.close();
            tempFile.delete();
        } catch (IOException e) {
            log.error(e.getMessage());
        }


        return amazonS3.getUrl(bucketName, objectName).toString();
    }

    private String uploadFirebase(MultipartFile file) {
        try {
//            MultipartFile file = uploadRequest.getFile();
            BlobId blobId = BlobId.of(bucketFirebase, Objects.requireNonNull(file.getOriginalFilename()));
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo, file.getBytes());

            // Optional: Get the public URL of the uploaded image
            return String.format("https://storage.googleapis.com/%s/%s", bucketFirebase, file.getOriginalFilename());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
