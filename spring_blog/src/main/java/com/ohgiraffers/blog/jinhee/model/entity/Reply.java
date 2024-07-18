package com.ohgiraffers.blog.jinhee.model.entity;

import jakarta.persistence.*;

@Entity
public class Reply extends Comment {

    @ManyToOne
    @JoinColumn(name = "parent_comment_id", nullable = false)
    private Comment parentComment;

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public Reply() {
    }

    public Reply(Comment parentComment) {
        this.parentComment = parentComment;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "parentComment=" + parentComment +
                '}';
    }
}
