package com.looment.uploadservice.configs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {
    @Value("${firebase.credentials.path}")
    private Resource firebaseCredentialsResource;

    @Value("${firebase.storage.bucket}")
    private String firebaseStorageBucket;

    @Bean
    public Storage firebaseStorage() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(firebaseCredentialsResource.getInputStream());
        StorageOptions options = StorageOptions.newBuilder().setCredentials(credentials).build();
        return options.getService();
    }

    @Bean
    public String firebaseStorageBucket() {
        return firebaseStorageBucket;
    }
}
