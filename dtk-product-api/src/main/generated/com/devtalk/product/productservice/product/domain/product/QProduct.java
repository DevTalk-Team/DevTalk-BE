package com.devtalk.product.productservice.product.domain.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 1254712116L;

    public static final QProduct product = new QProduct("product");

    public final com.devtalk.product.productservice.global.vo.QBaseTime _super = new com.devtalk.product.productservice.global.vo.QBaseTime(this);

    public final NumberPath<Long> consultantId = createNumber("consultantId", Long.class);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final NumberPath<Integer> F2FCost = createNumber("F2FCost", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedAt = _super.lastModifiedAt;

    public final NumberPath<Integer> NF2FCost = createNumber("NF2FCost", Integer.class);

    public final EnumPath<ProductProceedType> productProceedType = createEnum("productProceedType", ProductProceedType.class);

    public final DateTimePath<java.time.LocalDateTime> reservationAt = createDateTime("reservationAt", java.time.LocalDateTime.class);

    public final StringPath status = createString("status");

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

