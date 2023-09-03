package com.devtalk.product.productservice.product.domain.member;

import com.devtalk.product.productservice.global.vo.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SuperBuilder // 👈 여기 추가
public abstract class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberType memberType;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 20, unique = true)
    private String phoneNumber;

}