package com.devtalk.product.productservice.product.domain.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Consultant {

    @Id
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, length = 20)
    private int Nf2f;

    @Column(nullable = false, length = 20)
    private int f2f;

    @Column(nullable = false, length = 20)
    private String area;
    public static Consultant createConsultant(Long memberId, int Nf2f, int f2f,
                                              String area) {
        return Consultant.builder()
                .id(memberId)
                .Nf2f(Nf2f)
                .f2f(f2f)
                .area(area)
                .build();
    }
    public int getPrice(String type) {
        if (type == "Nf2f") {
            return this.Nf2f;
        }
        else {
            return this.f2f;
        }
    }
}

