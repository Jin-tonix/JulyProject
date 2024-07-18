package com.ohgiraffers.blog.jinhee.model.dto;

import com.ohgiraffers.blog.jinhee.model.entity.JinheeBlog;
import org.springframework.web.multipart.MultipartFile;

public class FileDTO {

    private Long id;
    private String originalFileName;
    private String savedName;
    private String savedFilePath;
    private String fileDescription;
    private MultipartFile multipartFile;
    private JinheeBlog jinheeBlog;

    public FileDTO() {}

    public FileDTO(Long id, String originalFileName, String savedName, String savedFilePath, String fileDescription, MultipartFile multipartFile, JinheeBlog jinheeBlog) {
        this.id = id;
        this.originalFileName = originalFileName;
        this.savedName = savedName;
        this.savedFilePath = savedFilePath;
        this.fileDescription = fileDescription;
        this.multipartFile = multipartFile;
        this.jinheeBlog = jinheeBlog;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getSavedName() {
        return savedName;
    }

    public void setSavedName(String savedName) {
        this.savedName = savedName;
    }

    public String getSavedFilePath() {
        return savedFilePath;
    }

    public void setSavedFilePath(String savedFilePath) {
        this.savedFilePath = savedFilePath;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public JinheeBlog getJinheeBlog() {
        return jinheeBlog;
    }

    public void setJinheeBlog(JinheeBlog jinheeBlog) {
        this.jinheeBlog = jinheeBlog;
    }

    @Override
    public String toString() {
        return "FileDTO{" +
                "id=" + id +
                ", originalFileName='" + originalFileName + '\'' +
                ", savedName='" + savedName + '\'' +
                ", savedFilePath='" + savedFilePath + '\'' +
                ", fileDescription='" + fileDescription + '\'' +
                ", multipartFile=" + multipartFile +
                ", jinheeBlog=" + jinheeBlog +
                '}';
    }
}
