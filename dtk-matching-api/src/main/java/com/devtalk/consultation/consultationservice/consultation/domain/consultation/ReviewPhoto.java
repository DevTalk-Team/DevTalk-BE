package com.devtalk.consultation.consultationservice.consultation.domain.consultation;

import com.devtalk.consultation.consultationservice.consultation.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewPhoto extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReviewPhoto_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Review_id")
    @JsonIgnore
    private Review review;

    private String fileUrl;

    private String originFileName;

    private String storedFileName;

    public void setConsultation(Review review) {
        this.review = review;
    }

    public static ReviewPhoto uploadReviewPhoto(Review review, String fileUrl, String originFileName, String storedFileName) {
        return ReviewPhoto.builder()
                .review(review)
                .fileUrl(fileUrl)
                .originFileName(originFileName)
                .storedFileName(storedFileName)
                .build();
    }
}
