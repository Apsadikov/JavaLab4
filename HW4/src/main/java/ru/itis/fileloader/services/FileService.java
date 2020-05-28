package ru.itis.fileloader.services;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Component
public interface FileService {
    String uploadFile(String email, MultipartFile file) throws IOException;

    File loadFile(String fileName) throws FileNotFoundException;
}
