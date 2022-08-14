package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileUploadMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.FileUpload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileUploadService {
    private FileUploadMapper fileMapper;
    private UserService userService;

    public FileUploadService(FileUploadMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }
    public Integer createFile(FileUpload fileUpload, String username){
        var user = userService.getUserInfo(username);
        fileUpload.setUserId(user.getUserid());
        var filename = fileMapper.getFilename(fileUpload.getUserId(), fileUpload.getFilename());
        if(filename != null){
            return 0;
        }
        return fileMapper.createFile(fileUpload);
    }

    public List<FileUpload> getFiles(String username){
        var user = userService.getUserInfo(username);
        return fileMapper.getFiles(user.getUserid());
    }
    public FileUpload getFileById(Integer id){
        return fileMapper.getFileById(id);
    }
    public void deleteFile(Integer fileId){
        fileMapper.deleteFile(fileId);
    }
}
