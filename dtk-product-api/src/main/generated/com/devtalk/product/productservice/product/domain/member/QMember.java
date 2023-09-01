package com.devtalk.product.productservice.product.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -356673038L;

    public static final QMember member = new QMember("member1");

    public final com.devtalk.product.productservice.global.vo.QBaseTime _super = new com.devtalk.product.productservice.global.vo.QBaseTime(this);

    //inherited
    public final StringPath createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedAt = _super.lastModifiedAt;

    public final StringPath loginId = createString("loginId");

    public final StringPath name = createString("name");

    public final EnumPath<RoleType> role = createEnum("role", RoleType.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

