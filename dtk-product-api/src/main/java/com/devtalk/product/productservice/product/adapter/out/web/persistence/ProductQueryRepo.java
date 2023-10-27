package com.devtalk.product.productservice.product.adapter.out.web.persistence;

import com.devtalk.product.productservice.product.application.port.out.repository.ProductQueryableRepo;
import com.devtalk.product.productservice.product.domain.product.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.devtalk.product.productservice.product.domain.product.QProduct.product;

@Repository
@RequiredArgsConstructor
class ProductQueryRepo implements ProductQueryableRepo {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Product> findByConsultantIdAndReservationAt(Long consultantId, LocalDate reservationDate, LocalTime reservationTime){
        return Optional.ofNullable(
                queryFactory
                .select(product)
                .from(product)
                .where(product.consultantId.eq(consultantId)
                        .and(product.reservationDate.eq(reservationDate))
                        .and(product.reservationTime.eq(reservationTime)))

                .fetchFirst());
    }

    @Override
    public List<Product> findByConsultantIdAndDate(Long consultantId, LocalDateTime date) {
        return null;
    }
}
