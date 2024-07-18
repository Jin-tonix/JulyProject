package com.ohgiraffers.blog.jaesuk.controller;

import com.ohgiraffers.blog.jaesuk.model.entity.JaesukBlog;
import com.ohgiraffers.blog.jaesuk.model.entity.JaesukComment;
import com.ohgiraffers.blog.jaesuk.service.JaesukLikeService;
import com.ohgiraffers.blog.jaesuk.service.JaesukService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/jaesuk")
public class JaesukController {

    private final JaesukService jaesukService;
    private final JaesukLikeService jaesukLikeService;
    private static final Logger logger = LoggerFactory.getLogger(JaesukController.class);

    @Autowired
    public JaesukController(JaesukService jaesukService, JaesukLikeService jaesukLikeService) {
        this.jaesukService = jaesukService;
        this.jaesukLikeService = jaesukLikeService;
    }

    @GetMapping("")
    public ModelAndView showMainPage() {
        ModelAndView mav = new ModelAndView("jaesuk/page");
        try {
            List<JaesukBlog> posts = jaesukService.getAllPosts();
            mav.addObject("posts", posts);
            logger.info("Showing main page with {} posts", posts.size());
        } catch (Exception e) {
            logger.error("Error fetching posts for main page", e);
            mav.addObject("error", "게시글을 불러오는 중 오류가 발생했습니다.");
        }
        return mav;
    }

    @GetMapping("/post")
    public String showPostForm(Model model) {
        model.addAttribute("post", new JaesukBlog());
        return "jaesuk/post";
    }

    @PostMapping("/post")
    public String createNewPost(@ModelAttribute JaesukBlog post, RedirectAttributes redirectAttributes) {
        try {
            JaesukBlog savedPost = jaesukService.createPost(post);
            redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 작성되었습니다.");
            return "redirect:/jaesuk";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "게시글 작성 중 오류가 발생했습니다.");
            return "redirect:/jaesuk/post";
        }
    }

    @GetMapping("/post/{id}")
    public ModelAndView viewPost(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("jaesuk/check");
        try {
            JaesukBlog post = jaesukService.getPostById(id);
            if (post != null) {
                mav.addObject("post", post);
                mav.addObject("newComment", new JaesukComment());
                logger.info("Viewing post with id: {}, title: {}", id, post.getTitle());
            } else {
                logger.warn("Post with id {} not found", id);
                mav.addObject("error", "게시글을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            logger.error("Error fetching post with id: {}", id, e);
            mav.addObject("error", "게시글을 불러오는 중 오류가 발생했습니다.");
        }
        return mav;
    }

    @PostMapping("/post/{id}/comment")
    public ModelAndView addComment(@PathVariable Integer id, @ModelAttribute JaesukComment comment, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("redirect:/jaesuk/post/" + id);
        try {
            jaesukService.addCommentToPost(id, comment);
            logger.info("Added comment to post with id: {}", id);
            redirectAttributes.addFlashAttribute("message", "댓글이 성공적으로 추가되었습니다.");
        } catch (Exception e) {
            logger.error("Error adding comment to post with id: {}", id, e);
            redirectAttributes.addFlashAttribute("error", "댓글 추가 중 오류가 발생했습니다.");
        }
        return mav;
    }

    @PostMapping("/post/{postId}/comment/{commentId}/delete")
    public String deleteComment(@PathVariable Integer postId, @PathVariable Long commentId, RedirectAttributes redirectAttributes) {
        try {
            jaesukService.deleteComment(postId, commentId);
            redirectAttributes.addFlashAttribute("message", "댓글이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            logger.error("Error deleting comment with id: {} from post with id: {}", commentId, postId, e);
            redirectAttributes.addFlashAttribute("error", "댓글 삭제 중 오류가 발생했습니다.");
        }
        return "redirect:/jaesuk/post/" + postId;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("jaesuk/edit");
        try {
            JaesukBlog post = jaesukService.getPostById(id);
            if (post != null) {
                mav.addObject("post", post);
                logger.info("Showing edit form for post with id: {}", id);
            } else {
                logger.warn("Post with id {} not found for editing", id);
                mav.setViewName("redirect:/jaesuk");
                mav.addObject("error", "수정할 게시글을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            logger.error("Error fetching post for editing with id: {}", id, e);
            mav.setViewName("redirect:/jaesuk");
            mav.addObject("error", "게시글을 불러오는 중 오류가 발생했습니다.");
        }
        return mav;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView updatePost(@PathVariable Integer id, @ModelAttribute JaesukBlog post, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("redirect:/jaesuk");
        try {
            JaesukBlog updatedPost = jaesukService.updatePost(id, post);
            if (updatedPost != null) {
                logger.info("Updated post with id: {}", id);
                redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 수정되었습니다.");
            } else {
                logger.warn("Failed to update post with id: {}", id);
                redirectAttributes.addFlashAttribute("error", "게시글 수정에 실패했습니다.");
            }
        } catch (Exception e) {
            logger.error("Error updating post with id: {}", id, e);
            redirectAttributes.addFlashAttribute("error", "게시글 수정 중 오류가 발생했습니다.");
        }
        return mav;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteConfirmation(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("jaesuk/delete");
        try {
            JaesukBlog post = jaesukService.getPostById(id);
            if (post != null) {
                mav.addObject("post", post);
                logger.info("Showing delete confirmation for post with id: {}", id);
            } else {
                logger.warn("Post with id {} not found for deletion", id);
                mav.setViewName("redirect:/jaesuk");
                mav.addObject("error", "삭제할 게시글을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            logger.error("Error fetching post for deletion with id: {}", id, e);
            mav.setViewName("redirect:/jaesuk");
            mav.addObject("error", "게시글을 불러오는 중 오류가 발생했습니다.");
        }
        return mav;
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deletePost(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("redirect:/jaesuk");
        try {
            boolean deleted = jaesukService.deletePost(id);
            if (deleted) {
                logger.info("Deleted post with id: {}", id);
                redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 삭제되었습니다.");
            } else {
                logger.warn("Failed to delete post with id: {}", id);
                redirectAttributes.addFlashAttribute("error", "게시글 삭제에 실패했습니다.");
            }
        } catch (Exception e) {
            logger.error("Error deleting post with id: {}", id, e);
            redirectAttributes.addFlashAttribute("error", "게시글 삭제 중 오류가 발생했습니다.");
        }
        return mav;
    }

    @PostMapping("/post/{id}/like")
    @ResponseBody
    public ResponseEntity<?> toggleLike(@PathVariable Integer id, @RequestParam String userId) {
        try {
            JaesukBlog blog = jaesukService.getPostById(id);
            boolean isLiked = jaesukLikeService.toggleLike(blog, userId);
            long likeCount = jaesukLikeService.getLikeCount(blog);
            logger.info("Toggled like for post id: {}, user: {}, isLiked: {}, likeCount: {}", id, userId, isLiked, likeCount);
            return ResponseEntity.ok(Map.of("liked", isLiked, "likeCount", likeCount));
        } catch (Exception e) {
            logger.error("Error toggling like for post id: {}, user: {}", id, userId, e);
            return ResponseEntity.badRequest().body("좋아요 처리 중 오류가 발생했습니다.");
        }
    }
}