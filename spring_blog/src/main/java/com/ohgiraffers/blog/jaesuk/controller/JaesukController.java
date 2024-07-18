package com.ohgiraffers.blog.jaesuk.controller;
import com.ohgiraffers.blog.jaesuk.model.dto.BlogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller

@RequestMapping("/jaesuk")
class JaesukController {

    private final com.ohgiraffers.blog.jaesuk.service.JaesukService JaesukService;

    @Autowired
    public JaesukController(com.ohgiraffers.blog.jaesuk.service.JaesukService jaesukService) {
        this.JaesukService = jaesukService;
    }

    @GetMapping
    public String indexJaesuk(){
        return "jaesuk/page";
    }

    @PostMapping
    public ModelAndView postBlog(BlogDTO blogDTO, ModelAndView mv){

        if(blogDTO.getBlogTitle() == null || blogDTO.getBlogTitle().equals("")){
            mv.setViewName("redirect:jaesuk");
        }
        if(blogDTO.getBlogContent() == null || blogDTO.getBlogContent().equals("")){
            mv.setViewName("redirect:jaesuk");
        }

        int result = JaesukService.post(blogDTO);

        if(result <= 0){
            mv.setViewName("error/page");
        }else{
            mv.setViewName("jaesuk/page");
        }

        return mv;
    }


}
