package com.bikkadit.electronic.store.ElectronicStore.services.impl;

import com.bikkadit.electronic.store.ElectronicStore.exceptions.BadApiRequestException;
import com.bikkadit.electronic.store.ElectronicStore.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadImage(MultipartFile file, String path) throws IOException {
        String originalFileName = file.getOriginalFilename();
        logger.info("FileName : {} ", originalFileName);
        String filename = UUID.randomUUID().toString();
        String extention = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileNameWithExtention = filename + extention;
        String fullPathWithFileName = path + File.separator + fileNameWithExtention;

        if (extention.equalsIgnoreCase(".png") || extention.equalsIgnoreCase(".jpg") || extention.equalsIgnoreCase(".jpeg")) {
            // file save
            File folder = new File(path);

            if (!folder.exists()) {
                // create the folder
                folder.mkdirs();
            }
                //upload
                Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
                return fileNameWithExtention;

        } else {

            throw new BadApiRequestException("File with this " + extention + " not allowed !!");
        }
    }
        @Override
        public InputStream getResource (String path, String name) throws FileNotFoundException {

            String fullPath = path + File.separator + name;
            InputStream inputStream = new FileInputStream(fullPath);
            return inputStream;
        }
    }
