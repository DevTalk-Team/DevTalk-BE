package com.devtalk.member.memberservice.member.domain.field;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QField is a Querydsl query type for Field
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QField extends EntityPathBase<Field> {

    private static final long serialVersionUID = -1911827901L;

    public static final QField field = new QField("field");

    public final StringPath fieldName = createString("fieldName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QField(String variable) {
        super(Field.class, forVariable(variable));
    }

    public QField(Path<? extends Field> path) {
        super(path.getType(), path.getMetadata());
    }

    public QField(PathMetadata metadata) {
        super(Field.class, metadata);
    }

}

