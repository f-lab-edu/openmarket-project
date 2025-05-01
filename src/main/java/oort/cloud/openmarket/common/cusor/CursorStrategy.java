package oort.cloud.openmarket.common.cusor;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import oort.cloud.openmarket.products.enums.ProductsCursorField;

import java.util.List;
import java.util.Optional;

public interface CursorStrategy {

    Optional<BooleanExpression> getBooleanExpression(Cursor<? extends CursorField> cursor);
    List<OrderSpecifier<?>> getOrderSpecifiers();
}
