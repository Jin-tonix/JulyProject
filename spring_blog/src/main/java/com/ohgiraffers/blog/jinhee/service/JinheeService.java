package com.ohgiraffers.blog.jinhee.service;

import com.ohgiraffers.blog.jinhee.model.dto.BlogDTO;
import com.ohgiraffers.blog.jinhee.model.dto.FileDTO;
import com.ohgiraffers.blog.jinhee.model.entity.File;
import com.ohgiraffers.blog.jinhee.model.entity.JinheeBlog;
import com.ohgiraffers.blog.jinhee.repository.FileDTORepository;
import com.ohgiraffers.blog.jinhee.repository.JinheeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class JinheeService {

    private final JinheeRepository jinheeRepository;
    private final FileDTORepository fileDTORepository; // 추가

    @Autowired
    public JinheeService(JinheeRepository jinheeRepository, FileDTORepository fileDTORepository) { // 수정
        this.jinheeRepository = jinheeRepository;
        this.fileDTORepository = fileDTORepository; // 초기화
    }

    // 나머지 코드...
    @Transactional
    public int post(BlogDTO blogDTO, List<MultipartFile> multipartFiles) throws IOException {
        JinheeBlog newBlog = new JinheeBlog();
        newBlog.setBlogTitle(blogDTO.getBlogTitle());
        newBlog.setBlogContent(blogDTO.getBlogContent());
        newBlog.setCreateDate(new Date());

        JinheeBlog savedBlog = jinheeRepository.save(newBlog);

        if (multipartFiles != null && !multipartFiles.isEmpty()) {
            List<File> files = saveFiles(multipartFiles, savedBlog);

            // If you need FileDTOs for some reason:
            // List<FileDTO> fileDTOs = convertToFileDTOs(files);

            savedBlog.setImageFiles(files); // Set files directly to blog entity

            // Or directly save File entities if needed:
            // savedBlog.setImageFiles(convertToFileEntities(fileDTOs));
        }

        jinheeRepository.save(savedBlog);
        return savedBlog != null ? 1 : 0;
    }


    public List<BlogDTO> getAllBlogs() {
        List<JinheeBlog> jinheeBlogs = jinheeRepository.findAll();
        List<BlogDTO> blogDTOs = new ArrayList<>();

        for (JinheeBlog blog : jinheeBlogs) {
            BlogDTO blogDTO = new BlogDTO();
            blogDTO.setId(blog.getId());
            blogDTO.setBlogTitle(blog.getBlogTitle());
            blogDTO.setBlogContent(blog.getBlogContent());
            blogDTO.setCreateDate(blog.getCreateDate());
            blogDTOs.add(blogDTO);
        }

        return blogDTOs;
    }

    public BlogDTO getBlogById(Long id) {
        JinheeBlog blog = jinheeRepository.findById(id).orElse(null);
        if (blog != null) {
            BlogDTO blogDTO = new BlogDTO();
            blogDTO.setId(blog.getId());
            blogDTO.setBlogTitle(blog.getBlogTitle());
            blogDTO.setBlogContent(blog.getBlogContent());
            blogDTO.setCreateDate(blog.getCreateDate());
            return blogDTO;
        }
        return null;
    }

    @Transactional
    public void deleteBlogById(Long id) {
        jinheeRepository.deleteById(id);
    }

    @Transactional
    public void updateBlog(BlogDTO blogDTO) {
        JinheeBlog blog = jinheeRepository.findById(blogDTO.getId()).orElse(null);
        if (blog != null) {
            blog.setBlogTitle(blogDTO.getBlogTitle());
            blog.setBlogContent(blogDTO.getBlogContent());
            jinheeRepository.save(blog);
        }
    }

    @Transactional
    public void likePost(Long id) {
        JinheeBlog blog = jinheeRepository.findById(id).orElse(null);
        if (blog != null) {
            blog.setLikes(blog.getLikes() + 1); // 좋아요 수 증가
            jinheeRepository.save(blog);
        }
    }

    public int getLikes(Long id) {
        JinheeBlog blog = jinheeRepository.findById(id).orElse(null);
        return blog != null ? blog.getLikes() : 0;
    }


    public List<File> saveFiles(List<MultipartFile> multipartFiles, JinheeBlog blog) throws IOException {
        String uploadDir = "./static/img/multi";
        List<File> savedFiles = new ArrayList<>();

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                String originalFileName = multipartFile.getOriginalFilename();
                String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
                String savedName = UUID.randomUUID().toString().replace("-", "") + ext;
                Path filePath = uploadPath.resolve(savedName);

                Files.copy(multipartFile.getInputStream(), filePath);

                String savedFilePath = uploadDir + "./static/img/multi" + savedName;
                String fileDescription = "File uploaded";

                File savedFile = new File(); // Create File entity object
                savedFile.setOriginalFileName(originalFileName);
                savedFile.setSavedName(savedName);
                savedFile.setSavedFilePath(savedFilePath);
                savedFile.setFileDescription(fileDescription);
                savedFile.setJinheeBlog(blog);

                fileDTORepository.save(savedFile); // Save File entity

                savedFiles.add(savedFile);
            }
        }

        return savedFiles;
    }

    private List<FileDTO> convertToFileDTOs(List<File> files) {
        List<FileDTO> fileDTOs = new ArrayList<>();
        for (File file : files) {
            FileDTO fileDTO = new FileDTO();
            fileDTO.setOriginalFileName(file.getOriginalFileName());
            fileDTO.setSavedName(file.getSavedName());
            fileDTO.setSavedFilePath(file.getSavedFilePath());
            fileDTO.setFileDescription(file.getFileDescription());
            fileDTO.setJinheeBlog(file.getJinheeBlog());
            fileDTOs.add(fileDTO);
        }
        return fileDTOs;
    }
}
