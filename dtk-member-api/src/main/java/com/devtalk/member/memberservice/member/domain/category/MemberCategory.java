package com.devtalk.member.memberservice.member.domain.category;

import com.devtalk.member.memberservice.member.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MEMBER_CATEGORY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class MemberCategory {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_CATEGORY_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    public static MemberCategory createMemberCategory(Member member, Category category) {
        return MemberCategory.builder()
                .member(member)
                .category(category)
                .build();
    }
}
