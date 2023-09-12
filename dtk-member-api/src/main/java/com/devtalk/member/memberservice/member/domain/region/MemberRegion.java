package com.devtalk.member.memberservice.member.domain.region;

import com.devtalk.member.memberservice.member.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "MEMBER_REGION")
@Getter
public class MemberRegion {
    @Id
    @GeneratedValue
    @Column(name = "MEMBER_REGION_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REGION_ID")
    private Region region;

    public static MemberRegion createMemberRegion(Member member, Region region) {
        MemberRegion memberRegion = new MemberRegion();
        memberRegion.member = member;
        memberRegion.region = region;
        return  memberRegion;
    }
}
