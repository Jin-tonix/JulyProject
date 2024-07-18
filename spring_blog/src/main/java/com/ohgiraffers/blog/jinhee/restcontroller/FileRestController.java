package com.ohgiraffers.blog.jinhee.restcontroller;

import com.ohgiraffers.blog.jinhee.model.dto.BlogDTO;
import com.ohgiraffers.blog.jinhee.model.dto.FileDTO;
import com.ohgiraffers.blog.jinhee.model.entity.JinheeBlog;
import com.ohgiraffers.blog.jinhee.service.JinheeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/file")
public class FileRestController {

    private final JinheeService jinheeService;

    @Autowired
    public FileRestController(JinheeService jinheeService) {
        this.jinheeService = jinheeService;
    }

    @PostMapping("/upload")
    public List<FileDTO> uploadFiles(@RequestParam("files") List<MultipartFile> files,
                                     @RequestParam("blogId") Long blogId) throws IOException {
        BlogDTO blogDTO = jinheeService.getBlogById(blogId);
        if (blogDTO != null) {
            JinheeBlog blog = convertToJinheeBlog(blogDTO); // Convert BlogDTO to JinheeBlog
            List<FileDTO> fileDTOs = jinheeService.saveFiles(files, blog).stream()
                    .map(this::convertToFileDTO)
                    .collect(Collectors.toList());
            return fileDTOs;
        }
        return null;
    }

    private JinheeBlog convertToJinheeBlog(BlogDTO blogDTO) {
        JinheeBlog blog = new JinheeBlog();
        blog.setId(blogDTO.getId());
        blog.setBlogTitle(blogDTO.getBlogTitle());
        blog.setBlogContent(blogDTO.getBlogContent());
        // Set other properties if needed
        return blog;
    }

    private FileDTO convertToFileDTO(com.ohgiraffers.blog.jinhee.model.entity.File file) {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setId(file.getId());
        fileDTO.setOriginalFileName(file.getOriginalFileName());
        fileDTO.setSavedName(file.getSavedName());
        fileDTO.setSavedFilePath(file.getSavedFilePath());
        fileDTO.setFileDescription(file.getFileDescription());
        return fileDTO;
    }
}
