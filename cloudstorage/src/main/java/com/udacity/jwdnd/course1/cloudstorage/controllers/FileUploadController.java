package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.FileUpload;
import com.udacity.jwdnd.course1.cloudstorage.services.FileUploadService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Controller
@RequestMapping("/fileUpload")
public class FileUploadController {
    private FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping()
    public String fileUpload(Authentication auth, @RequestParam("fileUpload")MultipartFile fileUpload, Model model) throws IOException {
        if(!fileUpload.isEmpty()){
            FileUpload file = new FileUpload(null, null, fileUpload.getOriginalFilename(),
                    fileUpload.getContentType(), String.valueOf(fileUpload.getSize()), fileUpload.getBytes());

            Integer id = fileUploadService.createFile(file, auth.getName());
            if(id == 0){
                model.addAttribute("isExists", "true");
            }
            else{
                model.addAttribute("isExists", "false");
            }
            model.addAttribute("files", fileUploadService.getFiles(auth.getName()));
        }
        handleFileAttribute(model);
        return "home";
    }
    private void handleFileAttribute(Model model){
        model.addAttribute("fileActive", "active show");
        model.addAttribute("fileSelected", "true");
    }

    @GetMapping("/deleteFile/{deletedFileId}")
    public String deleteFile(Authentication auth, @PathVariable(value = "deletedFileId") Integer fileId, Model model){
        fileUploadService.deleteFile(fileId);
        handleFileAttribute(model);
        model.addAttribute("files", fileUploadService.getFiles(auth.getName()));
        return "home";
    }
/*    @GetMapping("/viewFile/{id}")
    public void viewFile(Authentication auth, @PathVariable(value = "id") Integer fileId, HttpServletResponse resp) throws IOException {
        var file = fileUploadService.getFileById(fileId);
        var fileBytes = file.getFileData();
        resp.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM.getType());
        resp.setHeader("Content-Disposition", "inline; filename=" + file.getFilename());
        resp.setContentLength(fileBytes.length);

        OutputStream os = resp.getOutputStream();
        try {
            os.write(fileBytes, 0, fileBytes.length);
        } finally {
            os.close();
        }
    }*/
    @GetMapping("/viewFile/{id}")
    public ResponseEntity<byte[]> viewFile(@PathVariable(value = "id") Integer fileId) {

        var file = fileUploadService.getFileById(fileId);
        var fileBytes = file.getFileData();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = file.getFilename();

        headers.add("content-disposition", "inline;filename=" + filename);
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(fileBytes, headers, HttpStatus.OK);
        return response;
    }
}
