package com.ohgiraffers.blog.jinhee.model.dto;

public class ReplyDTO {

    private Long id;
    private String content;

    public ReplyDTO() {
    }

    public ReplyDTO(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ReplyDTO{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}