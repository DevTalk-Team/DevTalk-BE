package com.devtalk.board.consultationboardservice.board.domain.comment;

import com.devtalk.board.consultationboardservice.board.domain.BaseEntity;
import com.devtalk.board.consultationboardservice.board.domain.post.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post postId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String content;

    public void modify(String content) {
        this.content = content;
    }
}
