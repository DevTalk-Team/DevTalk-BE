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

    @Column(nullable = false)
    private String consulterName;

    @Column(nullable = false)
    private Long consultantId;

    @Column(nullable = false)
    private String consultantName;

    @Column(nullable = false)
    private Integer score;


    @Column(length = 255)
    private String content;

    public static Review createReview(Long consulterId, String consulterName, Long consultantId, String consultantName,
                                      Integer score, String content) {
        return Review.builder()
                .consulterId(consulterId)
                .consulterName(consulterName)
                .consultantId(consultantId)
                .consultantName(consultantName)
                .score(score)
                .content(content)
                .build();
    }
}
