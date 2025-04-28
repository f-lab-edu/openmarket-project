package oort.cloud.openmarket.common.cusor;

import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import oort.cloud.openmarket.products.controller.response.ProductsResponse;

public interface CursorField {
    String getFieldName();
    Class<? extends Comparable<?>> getType();
}
