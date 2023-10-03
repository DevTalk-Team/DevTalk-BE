package com.devtalk.board.consultationboardservice.board.domain.post;

import com.devtalk.board.consultationboardservice.board.domain.BaseEntity;
import com.devtalk.board.consultationboardservice.board.domain.attachedfile.AttachedFile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private Integer views;

    @Column(nullable = false)
    private Integer commentCount;

    public static Post createPost(String title, String content, Long userId, String userName) {
        return Post.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .userName(userName)
                .views(0)
                .commentCount(0)
                .build();
    }

    public void increaseViews() {
        this.views++;
    }

    public void increaseCommentCount() {
        this.commentCount++;
    }

    public void decreaseCommentCount(){
        this.commentCount--;
    }

    public void modify(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
