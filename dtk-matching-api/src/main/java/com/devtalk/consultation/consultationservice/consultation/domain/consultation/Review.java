package com.devtalk.consultation.consultationservice.consultation.domain.consultation;

import com.devtalk.consultation.consultationservice.global.vo.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Review extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long consulterId;

    private Integer score;

    private String photoUrl;

    private String photoOriginName;

    private String photoStoredName;

    @Column(length = 255)
    private String content;

    public static Review createReview(Long consulterId, Integer score, String photoUrl, String photoOriginName, String photoStoredName, String content) {
        return Review.builder()
                .consulterId(consulterId)
                .score(score)
                .photoUrl(photoUrl)
                .photoOriginName(photoOriginName)
                .photoStoredName(photoStoredName)
                .content(content)
                .build();
    }
}
