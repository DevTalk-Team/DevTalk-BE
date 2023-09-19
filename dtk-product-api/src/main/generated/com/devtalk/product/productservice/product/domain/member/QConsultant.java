package com.devtalk.product.productservice.product.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QConsultant is a Querydsl query type for Consultant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConsultant extends EntityPathBase<Consultant> {

    private static final long serialVersionUID = 63146451L;

    public static final QConsultant consultant = new QConsultant("consultant");

    public final QMember _super = new QMember(this);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final NumberPath<Long> f2f_Price = createNumber("f2f_Price", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedAt = _super.lastModifiedAt;

    //inherited
    public final EnumPath<MemberType> memberType = _super.memberType;

    //inherited
    public final StringPath name = _super.name;

    public final NumberPath<Long> nf2f_Price = createNumber("nf2f_Price", Long.class);

    //inherited
    public final StringPath phoneNumber = _super.phoneNumber;

    public final StringPath region = createString("region");

    public QConsultant(String variable) {
        super(Consultant.class, forVariable(variable));
    }

    public QConsultant(Path<? extends Consultant> path) {
        super(path.getType(), path.getMetadata());
    }

    public QConsultant(PathMetadata metadata) {
        super(Consultant.class, metadata);
    }

}

