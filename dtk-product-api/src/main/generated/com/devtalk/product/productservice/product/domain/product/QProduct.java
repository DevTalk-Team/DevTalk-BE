package com.devtalk.product.productservice.product.domain.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 1254712116L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final com.devtalk.product.productservice.global.vo.QBaseTime _super = new com.devtalk.product.productservice.global.vo.QBaseTime(this);

    public final com.devtalk.product.productservice.product.domain.member.QConsultant consultant;

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedAt = _super.lastModifiedAt;

    public final DateTimePath<java.time.LocalDateTime> reservationAt = createDateTime("reservationAt", java.time.LocalDateTime.class);

    public final StringPath status = createString("status");

    public final EnumPath<ProductProceedType> type = createEnum("type", ProductProceedType.class);

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.consultant = inits.isInitialized("consultant") ? new com.devtalk.product.productservice.product.domain.member.QConsultant(forProperty("consultant")) : null;
    }

}

