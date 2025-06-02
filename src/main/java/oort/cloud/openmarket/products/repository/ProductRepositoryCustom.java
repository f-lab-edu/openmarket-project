package oort.cloud.openmarket.products.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import oort.cloud.openmarket.products.controller.response.ProductsResponse;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductsResponse> findByCategoryWithSortAndCursor(
            Long categoryId,
            BooleanExpression cursorCondition,
            List<OrderSpecifier<?>> orderSpecifiers,
            int size
    );
}
