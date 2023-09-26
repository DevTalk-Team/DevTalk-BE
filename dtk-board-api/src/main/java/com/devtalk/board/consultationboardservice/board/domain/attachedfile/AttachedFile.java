package com.devtalk.board.consultationboardservice.board.domain.attachedfile;

import com.devtalk.board.consultationboardservice.board.domain.BaseEntity;
import com.devtalk.board.consultationboardservice.board.domain.post.Post;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AttachedFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attached_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String fileUrl;

    private String originFileName;

    private String storedFileName;

    public static AttachedFile createAttachedFile(Post post, String fileUrl, String originFileName, String storedFileName) {
        return AttachedFile.builder()
                .post(post)
                .fileUrl(fileUrl)
                .originFileName(originFileName)
                .storedFileName(storedFileName)
                .build();
    }
}
