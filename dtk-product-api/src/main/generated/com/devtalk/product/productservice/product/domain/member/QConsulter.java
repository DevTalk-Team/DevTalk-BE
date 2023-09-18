package com.devtalk.product.productservice.product.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QConsulter is a Querydsl query type for Consulter
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConsulter extends EntityPathBase<Consulter> {

    private static final long serialVersionUID = -1660530879L;

    public static final QConsulter consulter = new QConsulter("consulter");

    public final QMember _super = new QMember(this);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedAt = _super.lastModifiedAt;

    //inherited
    public final EnumPath<MemberType> memberType = _super.memberType;

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final StringPath phoneNumber = _super.phoneNumber;

    public QConsulter(String variable) {
        super(Consulter.class, forVariable(variable));
    }

    public QConsulter(Path<? extends Consulter> path) {
        super(path.getType(), path.getMetadata());
    }

    public QConsulter(PathMetadata metadata) {
        super(Consulter.class, metadata);
    }

}

