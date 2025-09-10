package com.ektaara.open_gem_gem.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    public String uploadFile(MultipartFile file);
}
