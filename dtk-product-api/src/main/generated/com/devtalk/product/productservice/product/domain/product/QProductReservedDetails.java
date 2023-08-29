package com.devtalk.product.productservice.product.domain.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductReservedDetails is a Querydsl query type for ProductReservedDetails
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductReservedDetails extends EntityPathBase<ProductReservedDetails> {

    private static final long serialVersionUID = -1309104090L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductReservedDetails productReservedDetails = new QProductReservedDetails("productReservedDetails");

    public final QProduct _super;

    public final StringPath area = createString("area");

    // inherited
    public final com.devtalk.product.productservice.product.domain.member.QConsultant consultant;

    public final NumberPath<Long> consulterId = createNumber("consulterId", Long.class);

    //inherited
    public final StringPath createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedAt;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final QProduct product;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> reservationAt;

    public final EnumPath<ReservedProceedType> reservedProceedType = createEnum("reservedProceedType", ReservedProceedType.class);

    //inherited
    public final StringPath status;

    //inherited
    public final EnumPath<ProductProceedType> type;

    public QProductReservedDetails(String variable) {
        this(ProductReservedDetails.class, forVariable(variable), INITS);
    }

    public QProductReservedDetails(Path<? extends ProductReservedDetails> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductReservedDetails(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductReservedDetails(PathMetadata metadata, PathInits inits) {
        this(ProductReservedDetails.class, metadata, inits);
    }

    public QProductReservedDetails(Class<? extends ProductReservedDetails> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QProduct(type, metadata, inits);
        this.consultant = _super.consultant;
        this.createdAt = _super.createdAt;
        this.lastModifiedAt = _super.lastModifiedAt;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
        this.reservationAt = _super.reservationAt;
        this.status = _super.status;
        this.type = _super.type;
    }

}

