package com.ohgiraffers.blog.hwayeon.service;

import com.ohgiraffers.blog.hwayeon.model.dto.CommentDTO;
import com.ohgiraffers.blog.hwayeon.model.dto.HwayeonBlogDTO;
import com.ohgiraffers.blog.hwayeon.model.entity.Comment;
import com.ohgiraffers.blog.hwayeon.model.entity.HwayeonBlog;
import com.ohgiraffers.blog.hwayeon.repository.CommentRepository;
import com.ohgiraffers.blog.hwayeon.repository.HwayeonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HwayeonService {

    private final HwayeonRepository hwayeonRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public HwayeonService(HwayeonRepository hwayeonRepository,CommentRepository commentRepository) {
        this.hwayeonRepository = hwayeonRepository;
        this.commentRepository = commentRepository;
    }

    // 모든 게시글 목록 조회
    public List<HwayeonBlogDTO> findAllPosts() {
        // 데이터베이스에서 모든 게시글을 조회하여 HwayeonBlog 객체의 리스트로 받아옴
        List<HwayeonBlog> allBlogs = hwayeonRepository.findAll();

        // HwayeonBlog 객체 리스트를 HwayeonBlogDTO 객체 리스트로 변환하여 반환
        return allBlogs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 게시글 저장

    public void savePost(HwayeonBlogDTO blogDTO) {
        // HwayeonBlogDTO 객체에서 데이터를 추출하여 HwayeonBlog 객체에 설정

    public HwayeonBlogDTO savePost(HwayeonBlogDTO blogDTO) {

        HwayeonBlog blog = new HwayeonBlog();
        // DTO to Entity 변환
        blog = hwayeonRepository.save(blog);
        return convertToDTO(blog);  // 저장된 엔티티를 DTO로 변환하여 반환
    }
//    public HwayeonBlogDTO savePost(HwayeonBlogDTO blogDTO) {
//        // HwayeonBlogDTO 객체에서 데이터를 추출하여 HwayeonBlog 객체에 설정
//        HwayeonBlog blog = new HwayeonBlog();
//
//        blog.setCreateDate(blogDTO.getCreateDate());
//        blog.setBlogTitle(blogDTO.getBlogTitle());
//        blog.setBlogContent(blogDTO.getBlogContent());
//        blog.setBlogNo(blogDTO.getBlogNo());
//
//        // 설정된 HwayeonBlog 객체를 저장
//        blog = hwayeonRepository.save(blog);
//
//        // 저장 후 생성된 blogNo를 포함한 DTO 반환
//        return convertToDTO(blog);
//    }


    public HwayeonBlogDTO savePost(HwayeonBlogDTO blogDTO) {
        HwayeonBlog blog = new HwayeonBlog();
        // DTO to Entity 변환
        blog = hwayeonRepository.save(blog);
        return convertToDTO(blog);  // 저장된 엔티티를 DTO로 변환하여 반환
    }
//    public HwayeonBlogDTO savePost(HwayeonBlogDTO blogDTO) {
//        // HwayeonBlogDTO 객체에서 데이터를 추출하여 HwayeonBlog 객체에 설정
//        HwayeonBlog blog = new HwayeonBlog();
//
////        blog.setCreateDate(blogDTO.getCreateDate());
//        blog.setBlogTitle(blogDTO.getBlogTitle());
//        blog.setBlogContent(blogDTO.getBlogContent());
//        blog.setBlogNo(blogDTO.getBlogNo());
//
//        // 설정된 HwayeonBlog 객체를 저장
//        blog = hwayeonRepository.save(blog);
//
//        // 저장 후 생성된 blogNo를 포함한 DTO 반환
//        return convertToDTO(blog);
//    }

    // 게시글 번호로 게시글 조회
    public HwayeonBlogDTO findByBlogNo(Integer blogNo) {
        // 게시글 번호로 데이터베이스에서 게시글 조회, 결과가 없으면 예외 발생
        HwayeonBlog blog = hwayeonRepository.findById(blogNo)

                .orElseThrow(() -> new RuntimeException("Blog를 찾을 수 없습니다."));
                .orElseThrow(() -> new RuntimeException("Blog를 찾을 수 없습니다. BlogNo: " + blogNo));

        System.out.println("Found blog in repository: " + blog); // 로그 추가
                .orElseThrow(() -> new RuntimeException("Blog를 찾을 수 없습니다. BlogNo: " + blogNo));

        System.out.println("레파지토리에서 찾아온 blogNo : " + blog); // 로그 추가

        // 조회된 HwayeonBlog 객체를 HwayeonBlogDTO 객체로 변환하여 반환
        return convertToDTO(blog);
    }

    // 게시글 수정

    public boolean updatePost(HwayeonBlogDTO blogDTO) {
        Integer blogNo = blogDTO.getBlogNo(); // postDto에서 ID 추출
        if (blogNo == null) {
            throw new IllegalArgumentException("Post ID must not be null");
    @Transactional
    public boolean updatePost(HwayeonBlogDTO blogDTO) {

    @Transactional
    public boolean updatePost(HwayeonBlogDTO blogDTO) {
        try {
            HwayeonBlog blog = hwayeonRepository.findById(blogDTO.getBlogNo())
                    .orElseThrow(() -> new RuntimeException("Blog를 찾을 수 없습니다."));

            blog.setBlogTitle(blogDTO.getBlogTitle());
            blog.setBlogContent(blogDTO.getBlogContent());

            blog.setImgUrl(blogDTO.getImgUrl());
            blog.setCategory(blogDTO.getCategory());
            blog.setLikes(blogDTO.getLikes());

            hwayeonRepository.save(blog);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }
//    public boolean updatePost(HwayeonBlogDTO blogDTO) {
//        Integer blogNo = blogDTO.getBlogNo(); // postDto에서 ID 추출
//        if (blogNo == null) {
//            throw new IllegalArgumentException("Post ID must not be null");
//        }
//
//        // 게시글 번호로 데이터베이스에서 게시글 조회, 결과가 없으면 예외 발생
//        HwayeonBlog blog = hwayeonRepository.findById(blogNo)
//                .orElseThrow(() -> new RuntimeException("Blog를 찾을 수 없습니다."));
//
//        // HwayeonBlogDTO 객체에서 데이터를 추출하여 HwayeonBlog 객체에 설정
//        blog.setBlogTitle(blogDTO.getBlogTitle());
//        blog.setBlogContent(blogDTO.getBlogContent());
//
//        // 수정된 HwayeonBlog 객체를 저장
//        HwayeonBlog updatedBlog = hwayeonRepository.save(blog);
//
//        // 저장된 블로그 객체가 null이 아니면 수정이 성공적으로 이루어진 것으로 판단
//        return updatedBlog != null;
//    }


        // 게시글 번호로 데이터베이스에서 게시글 조회, 결과가 없으면 예외 발생
        HwayeonBlog blog = hwayeonRepository.findById(blogNo)
                .orElseThrow(() -> new RuntimeException("Blog를 찾을 수 없습니다."));

        // HwayeonBlogDTO 객체에서 데이터를 추출하여 HwayeonBlog 객체에 설정
        blog.setBlogTitle(blogDTO.getBlogTitle());
        blog.setBlogContent(blogDTO.getBlogContent());

        // 수정된 HwayeonBlog 객체를 저장
        HwayeonBlog updatedBlog = hwayeonRepository.save(blog);

        // 저장된 블로그 객체가 null이 아니면 수정이 성공적으로 이루어진 것으로 판단
        return updatedBlog != null;

        }

    }
//    public boolean updatePost(HwayeonBlogDTO blogDTO) {
//        Integer blogNo = blogDTO.getBlogNo(); // postDto에서 ID 추출
//        if (blogNo == null) {
//            throw new IllegalArgumentException("Post ID must not be null");
//        }
//
//        // 게시글 번호로 데이터베이스에서 게시글 조회, 결과가 없으면 예외 발생
//        HwayeonBlog blog = hwayeonRepository.findById(blogNo)
//                .orElseThrow(() -> new RuntimeException("Blog를 찾을 수 없습니다."));
//
//        // HwayeonBlogDTO 객체에서 데이터를 추출하여 HwayeonBlog 객체에 설정
//        blog.setBlogTitle(blogDTO.getBlogTitle());
//        blog.setBlogContent(blogDTO.getBlogContent());
//
//        // 수정된 HwayeonBlog 객체를 저장
//        HwayeonBlog updatedBlog = hwayeonRepository.save(blog);
//
//        // 저장된 블로그 객체가 null이 아니면 수정이 성공적으로 이루어진 것으로 판단
//        return updatedBlog != null;
//    }

    // HwayeonBlog 객체를 HwayeonBlogDTO 객체로 변환
    private HwayeonBlogDTO convertToDTO(HwayeonBlog blog) {
        return new HwayeonBlogDTO(
                blog.getBlogNo(),
                blog.getBlogTitle(),
                blog.getBlogContent(),
                blog.getCreateDate(),
                blog.getImgUrl(),
                blog.getCategory(),
                blog.getLikes()
        );
    }


    // HwayeonBlog 객체를 HwayeonBlogDTO 객체로 변환
    private HwayeonBlogDTO convertToDTO(HwayeonBlog blog) {
        return new HwayeonBlogDTO(
                blog.getBlogNo(),
                blog.getBlogTitle(),
                blog.getBlogContent(),
                blog.getCreateDate(),
                blog.getImgUrl(),
                blog.getCategory(),
                blog.getLikes()
        );
    }


    // HwayeonBlogDTO 객체를 HwayeonBlog 객체로 변환
    private HwayeonBlog convertToEntity(HwayeonBlogDTO blogDTO) {
        HwayeonBlog blog = new HwayeonBlog();
        blog.setBlogNo(blogDTO.getBlogNo());
        blog.setBlogTitle(blogDTO.getBlogTitle());
        blog.setBlogContent(blogDTO.getBlogContent());
        blog.setCreateDate(blogDTO.getCreateDate());
        blog.setImgUrl(blogDTO.getImgUrl());
        blog.setCategory(blogDTO.getCategory());
        blog.setLikes(blogDTO.getLikes());
        return blog;
    }


    // 게시글 삭제
    public boolean deletePost(Integer blogNo) {
        // 게시글 번호로 데이터베이스에서 게시글 삭제 시도
        try {
            hwayeonRepository.deleteById(blogNo);
            return true; // 삭제 성공
        } catch (Exception e) {
            e.printStackTrace();
            return false; // 삭제 실패
        }
    }

    @Transactional
    public void likePost(Integer blogNo) {
        HwayeonBlog blog = hwayeonRepository.findById(blogNo)
                .orElseThrow(() -> new RuntimeException("Blog를 찾을 수 없습니다."));
        blog.setLikes(blog.getLikes() + 1);
        hwayeonRepository.save(blog);
    }

    public HwayeonBlogDTO findBlogByNo(Integer blogNo) {
        HwayeonBlog blog = hwayeonRepository.findById(blogNo)
                .orElseThrow(() -> new RuntimeException("Blog를 찾을 수 없습니다. BlogNo: " + blogNo));
        return convertToDTO(blog);
    }

    // 댓글 추가 메서드
    public void addComment(CommentDTO commentDTO) {
        // 값이 넘어오지 않았을 때의 처리
        if (commentDTO == null) {
            throw new IllegalArgumentException("CommentDTO에 null값 안됨");
        }

        Comment comment = new Comment();

        // BlogNo 확인 및 설정
        if (commentDTO.getBlogNo() != null) {
            comment.setBlogNo(commentDTO.getBlogNo());
        } else {
            throw new IllegalArgumentException("BlogNo = null");
            // 또는 기본값 설정 또는 다른 처리 방법을 선택할 수 있음
        }

        // Author 확인 및 설정
        if (commentDTO.getAuthor() != null && !commentDTO.getAuthor().isEmpty()) {
            comment.setAuthor(commentDTO.getAuthor());
        } else {
            throw new IllegalArgumentException("Author == null or empty");
            // 또는 기본값 설정 또는 다른 처리 방법을 선택할 수 있음
        }

        // Content 확인 및 설정
        if (commentDTO.getContent() != null && !commentDTO.getContent().isEmpty()) {
            comment.setContent(commentDTO.getContent());
        } else {
            throw new IllegalArgumentException("Content == null or empty");
            // 또는 기본값 설정 또는 다른 처리 방법을 선택할 수 있음
        }

        // CreationDate 확인 및 설정
        if (commentDTO.getCreationDate() != null) {
            comment.setCreationDate(commentDTO.getCreationDate());
        } else {
            throw new IllegalArgumentException("CreationDate == null");
            // 또는 기본값 설정 또는 다른 처리 방법을 선택할 수 있음
        }

        // comment 저장
        commentRepository.save(comment);
    }
}
