package com.ektaara.open_gem_gem.config;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class GoogleCloudConfig {

    @Bean
    public Storage storage() throws IOException {
        return StorageOptions.newBuilder()
                .setProjectId("poised-conduit-471220-b4")
                .setCredentials(
                        ServiceAccountCredentials.fromStream(
                                new FileInputStream("/Users/ravi/Desktop/Ektaara/open-gem-gem/src/main/resources/poised-conduit-471220-b4-a1c06bd33301.json")
                        )
                )
                .build()
                .getService();
    }
}