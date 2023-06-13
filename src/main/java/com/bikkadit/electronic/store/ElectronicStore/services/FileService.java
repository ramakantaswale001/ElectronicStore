package com.bikkadit.electronic.store.ElectronicStore.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileService {

    public String uploadImage(MultipartFile file, String path);

    InputStream getResource (String path, String name);


}
