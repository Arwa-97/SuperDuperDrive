package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.FileUpload;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileUploadMapper {

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer createFile(FileUpload fileUpload);

    @Select("SELECT filename FROM FILES WHERE userid = #{userId} AND filename = #{filename}")
    String getFilename(Integer userId, String filename);

    @Select("SELECT * FROM FILES WHERE fileId = #{id}")
    FileUpload getFileById(Integer id);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<FileUpload> getFiles(Integer userId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void deleteFile(Integer fileId);
}
