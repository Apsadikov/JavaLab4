package ru.itis.fileloader.controllers;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.fileloader.services.FileService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public void uploadFile(@RequestPart("file") MultipartFile file, @RequestParam("mail") String mail) throws IOException {
        fileService.uploadFile(mail, file);
    }

    @RequestMapping(value = "/files/{file-name:.+}", method = RequestMethod.GET)
    public void getFile(@PathVariable("file-name") String fileName, HttpServletResponse response) throws IOException {
        File file = fileService.loadFile(fileName);
        if (file != null) {
            response.setContentType(Files.probeContentType(Paths.get(file.getPath())));
            IOUtils.copy(new FileInputStream(file), response.getOutputStream());
        }
    }

    @RequestMapping(value = "/upload-form", method = RequestMethod.GET)
    public String fileUpload() {
        return "/upload_form";
    }
}
