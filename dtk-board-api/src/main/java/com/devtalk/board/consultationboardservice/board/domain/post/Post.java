package com.devtalk.board.consultationboardservice.board.domain.post;

import com.devtalk.board.consultationboardservice.board.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long writerId;

    @Column(nullable = false)
    private Integer viewCount;

    public void increaseViewCount() {
        this.viewCount += 1;
    }
}
