package com.ohgiraffers.blog.jinhee.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "file")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(name = "original_file_name", nullable = false)
    private String originalFileName;

    @Column(name = "saved_name", nullable = false)
    private String savedName;

    @Column(name = "saved_file_path", nullable = false)
    private String savedFilePath;

    @Column(name = "file_description")
    private String fileDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private JinheeBlog jinheeBlog;

    public File() {}

    public File(String originalFileName, String savedName, String savedFilePath, String fileDescription) {
        this.originalFileName = originalFileName;
        this.savedName = savedName;
        this.savedFilePath = savedFilePath;
        this.fileDescription = fileDescription;
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

    public JinheeBlog getJinheeBlog() {
        return jinheeBlog;
    }

    public void setJinheeBlog(JinheeBlog jinheeBlog) {
        this.jinheeBlog = jinheeBlog;
    }
}
