package com.devtalk.product.productservice.product.adapter.out.web.persistence;

import com.devtalk.product.productservice.product.application.port.out.repository.ProductQueryableRepo;
import com.devtalk.product.productservice.product.domain.product.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class ProductQueryRepo implements ProductQueryableRepo {

}
