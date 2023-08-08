package com.looment.uploadservice.services;

import com.amazonaws.services.s3.AmazonS3;
import com.google.cloud.storage.*;
import com.looment.loomententity.entities.Uploads;
import com.looment.uploadservice.dtos.UploadRequest;
import com.looment.uploadservice.dtos.UploadResponse;
import com.looment.uploadservice.exceptions.FileEmpty;
import com.looment.uploadservice.repositories.UploadRepository;
import com.looment.uploadservice.utils.RandomString;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @Value("${firebase.storage.bucket}")
    private String bucketFirebase;

//    @Override
//    public UploadResponse uploadFileS3(UploadRequest uploadRequest) {
//        String url = uploadS3(bucketS3, uploadRequest.getFile());
//
//        return getUploadResponse(uploadRequest, url);
//    }

    @Override
    public UploadResponse uploadFileFirebase(UploadRequest uploadRequest) {
        if (uploadRequest.getFile().isEmpty()) {
            throw new FileEmpty();
        }
        String url = uploadFirebase(uploadRequest.getFile());

        return getUploadResponse(uploadRequest, url);
    }

    private UploadResponse getUploadResponse(UploadRequest uploadRequest, String url) {
        Uploads uploads = new Uploads();
        uploads.setFileName(uploadRequest.getFile().getOriginalFilename());
        uploads.setUrl(url);
        uploads.setType(uploadRequest.getFile().getContentType());
        uploads.setUploadedBy(uploadRequest.getUploadedBy().toString());
        uploadRepository.save(uploads);

        return modelMapper.map(uploads, UploadResponse.class);
    }

//    private String uploadS3(String bucketName, String objectName, MultipartFile file) {
//
//        try {
//            File tempFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
//            FileOutputStream os = new FileOutputStream(tempFile);
//            os.write(file.getBytes());
//            amazonS3.putObject(
//                    new PutObjectRequest(bucketName, objectName, tempFile)
//                            .withCannedAcl(CannedAccessControlList.PublicRead)
//            );
//            os.close();
//            tempFile.delete();
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
//
//
//        return amazonS3.getUrl(bucketName, objectName).toString();
//    }

    private String uploadFirebase(MultipartFile file) {
        try {
            String fileName = formatFileName();
            BlobId blobId = BlobId.of(bucketFirebase, Objects.requireNonNull(fileName));
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(file.getContentType())
                    .build();
            storage.create(blobInfo, file.getInputStream());
            storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
            return String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", bucketFirebase, fileName);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String formatFileName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");
        return String.format("Looment-%s-%s", now.format(formatter), RandomString.generateRandomString());
    }
}
