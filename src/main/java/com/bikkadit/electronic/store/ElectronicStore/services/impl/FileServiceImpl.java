package com.bikkadit.electronic.store.ElectronicStore.services.impl;

import com.bikkadit.electronic.store.ElectronicStore.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

public class FileServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    @Override
    public String uploadImage(MultipartFile file, String path) {
        String originalFileName = file.getOriginalFilename();
        logger.info("FileName : {} ",originalFileName);
        String filename = UUID.randomUUID().toString();
        String extention = originalFileName.substring(originalFileName.lastIndexOf());
        String fileNameWithExtention = filename+extention;
        String fullPathWithFileName = path + File.separator+fileNameWithExtention;

        if(extention.equalsIgnoreCase(".png") || extention.equalsIgnoreCase(".jpg") || extention.equalsIgnoreCase(".jpeg"))
        return null;
    }else{
        throw
    }

    @Override
    public InputStream getResource(String path, String name) {
        return null;
    }
}
