package com.devtalk.product.productservice.product.domain.member;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("CONSULTER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Consulter extends Member {

}
