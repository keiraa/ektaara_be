package com.ektaara.open_gem_gem.serviceImpl;

import com.ektaara.open_gem_gem.service.StorageService;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GCPStorageService implements StorageService {

    private final Storage storage;

    private final String bucketName = "ektaara";

    public String uploadFile(MultipartFile file) {
        try {
            String blobName = UUID.randomUUID() + "-" + file.getOriginalFilename();

            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, blobName)
                    .setContentType(file.getContentType())
                    .build();

            storage.create(blobInfo, file.getBytes());

            return String.format("https://storage.googleapis.com/%s/%s", bucketName, blobName);
        }
        catch (IOException exception){
            System.out.println("Exception occurred while uploading to google: " + exception.getMessage());
        }
        return null;
    }
}
