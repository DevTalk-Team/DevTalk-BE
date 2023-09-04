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

    public final StringPath area = createString("area");

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final NumberPath<Integer> F2F = createNumber("F2F", Integer.class);

    public final NumberPath<Integer> f2F = createNumber("f2F", Integer.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final StringPath lastModifiedAt = _super.lastModifiedAt;

    //inherited
    public final StringPath loginId = _super.loginId;

    //inherited
    public final StringPath name = _super.name;

    public final NumberPath<Integer> NF2F = createNumber("NF2F", Integer.class);

    //inherited
    public final EnumPath<RoleType> role = _super.role;

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

