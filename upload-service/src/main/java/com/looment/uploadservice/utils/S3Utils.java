//package com.looment.uploadservice.utils;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//
//@Component
//@RequiredArgsConstructor
//public class S3Utils {
//    private final AmazonS3 amazonS3;
//
//    public static String putObject(String bucketName, String objectName, File file) {
//        try {
//            amazonS3.putObject(
//                    new PutObjectRequest(bucketName, objectName, file)
//                            .withCannedAcl(CannedAccessControlList.PublicRead)
//            );
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage());
//        }
//
//        return amazonS3.getUrl(bucketName, objectName).toString();
//    }
//}
