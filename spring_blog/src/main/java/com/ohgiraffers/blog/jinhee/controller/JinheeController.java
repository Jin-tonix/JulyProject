package com.ohgiraffers.blog.jinhee.controller;

import com.ohgiraffers.blog.jinhee.model.dto.BlogDTO;
import com.ohgiraffers.blog.jinhee.model.dto.FileDTO;
import com.ohgiraffers.blog.jinhee.service.JinheeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/jinhee")
public class JinheeController {

    private final JinheeService jinheeService;

    @Autowired
    public JinheeController(JinheeService jinheeService) {
        this.jinheeService = jinheeService;
    }

    @GetMapping("/main")
    public String indexJinhee() {
        return "jinhee/main";
    }

    @GetMapping("/post")
    public String showPostForm(Model model) {
        model.addAttribute("blogDTO", new BlogDTO());
        return "jinhee/post";
    }

    @PostMapping("/post")
    public String postBlog(@ModelAttribute("blogDTO") BlogDTO blogDTO,
                           @RequestParam("files") List<MultipartFile> files,
                           RedirectAttributes redirectAttributes) throws IOException {
        int result = jinheeService.post(blogDTO, files);
        if (result <= 0) {
            return "redirect:/jinhee/error/page";
        } else {
            redirectAttributes.addFlashAttribute("confirm", true);
            return "redirect:/jinhee/journey";
        }
    }


    @GetMapping("/post/{id}")
    public String getPostPageById(@PathVariable Long id, Model model) {
        BlogDTO blogDTO = jinheeService.getBlogById(id); // Assuming you have this method in JinheeService
        if (blogDTO == null) {
            return "redirect:/jinhee/error/page"; // or handle accordingly
        }
        model.addAttribute("blogDTO", blogDTO);
        return "jinhee/postpage";
    }

    @GetMapping("/journey")
    public String share(Model model) {
        List<BlogDTO> blogs = jinheeService.getAllBlogs();
        model.addAttribute("blogs", blogs);
        return "jinhee/journey";
    }

    @GetMapping("/postpage")
    public String postPage(Model model) {
        // 필요시 currentBlog 사용 코드 추가
        return "jinhee/postpage";
    }

    @GetMapping("/postpage/{id}")
    public String postPage(@PathVariable Long id, Model model) {
        BlogDTO blogDTO = jinheeService.getBlogById(id);
        model.addAttribute("blogTitle", blogDTO.getBlogTitle());
        model.addAttribute("blogContent", blogDTO.getBlogContent());
        model.addAttribute("blogDTO", blogDTO);
        model.addAttribute("blogId", blogDTO.getId());
        model.addAttribute("likeCount", jinheeService.getLikes(id));
        model.addAttribute("imageFiles", blogDTO.getImageFiles());

        return "jinhee/postpage";
    }


    @PostMapping("/postpage/{id}/like")
    @ResponseBody
    public ResponseEntity<String> likePost(@PathVariable Long id) {
        // 좋아요 로직 처리
        jinheeService.likePost(id);

        // 클라이언트에게 JSON 형식으로 응답
        return ResponseEntity.ok("");
    }


    @GetMapping("/edit/{id}")
    public String editBlog(@PathVariable Long id, Model model) {
        BlogDTO blogDTO = jinheeService.getBlogById(id);
        if (blogDTO == null) {
            return "redirect:/jinhee/journey";
        }
        model.addAttribute("blogDTO", blogDTO);
        return "jinhee/edit";
    }

    @PostMapping("/edit/{id}")
    public String editSubmit(@ModelAttribute BlogDTO blogDTO) {
        jinheeService.updateBlog(blogDTO);
        return "redirect:/jinhee/journey";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable Long id, Model model) {
        BlogDTO blogDTO = jinheeService.getBlogById(id);
        if (blogDTO == null) {
            return "redirect:/jinhee/journey";
        }
        model.addAttribute("blogDTO", blogDTO);
        return "jinhee/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteConfirm(@PathVariable Long id, @RequestParam(name = "confirm", defaultValue = "false") boolean confirm, RedirectAttributes redirectAttributes) {
         jinheeService.deleteBlogById(id);
         return "redirect:/jinhee/journey";
    }


}
