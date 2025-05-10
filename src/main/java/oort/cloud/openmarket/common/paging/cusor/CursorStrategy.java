package oort.cloud.openmarket.common.paging.cusor;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;
import java.util.Optional;

public interface CursorStrategy {

    Optional<BooleanExpression> getBooleanExpression(Cursor<? extends CursorField> cursor);
    List<OrderSpecifier<?>> getOrderSpecifiers();
}
