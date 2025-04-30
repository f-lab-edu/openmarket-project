package oort.cloud.openmarket.products.enums;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import oort.cloud.openmarket.common.cusor.Cursor;
import oort.cloud.openmarket.common.cusor.CursorField;
import oort.cloud.openmarket.common.cusor.CursorStrategy;
import oort.cloud.openmarket.exception.business.NotFoundCursorSortStrategy;
import oort.cloud.openmarket.exception.enums.ErrorType;
import oort.cloud.openmarket.products.entity.QProducts;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public enum ProductCursorStrategy implements CursorStrategy {
    LATEST("latest",
            List.of(ProductsCursorField.SALES_AT, ProductsCursorField.PRODUCTS_ID),
            List.of(
                    QProducts.products.salesAt.desc(),
                    QProducts.products.productId.desc()
            )
    ){

        @Override
        public Optional<BooleanExpression> getBooleanExpression(Cursor<? extends CursorField> cursor) {
            LocalDateTime salesAtValue = cursor.get(ProductsCursorField.SALES_AT, LocalDateTime.class);
            Long productIdValue = cursor.get(ProductsCursorField.PRODUCTS_ID, Long.class);

            if (salesAtValue == null || productIdValue == null) {
                return Optional.empty();
            }

            return Optional.of(QProducts.products.salesAt.lt(salesAtValue)
                    .or(QProducts.products.salesAt.eq(salesAtValue)
                            .and(QProducts.products.productId.lt(productIdValue))));
        }
    }
    ,
    PRICE_ASC("price_asc",
            List.of(ProductsCursorField.PRICE, ProductsCursorField.PRODUCTS_ID),
            List.of(
                    QProducts.products.price.asc(),
                    QProducts.products.productId.desc()
            )
    ) {

        @Override
        public Optional<BooleanExpression> getBooleanExpression(Cursor<? extends CursorField> cursor) {
            Integer priceValue = cursor.get(ProductsCursorField.PRICE, Integer.class);
            Long productIdValue = cursor.get(ProductsCursorField.PRODUCTS_ID, Long.class);

            if(priceValue == null || productIdValue == null){
                return Optional.empty();
            }

            return Optional.of(QProducts.products.price.gt(priceValue)
                    .or(QProducts.products.price.eq(priceValue)
                            .and(QProducts.products.productId.lt(productIdValue))));
        }
    },
    PRICE_DESC("price_desc",
            List.of(ProductsCursorField.PRICE, ProductsCursorField.PRODUCTS_ID),
            List.of(
                    QProducts.products.price.desc(),
                    QProducts.products.productId.desc()
            )
    ) {
        @Override
        public Optional<BooleanExpression> getBooleanExpression(Cursor<? extends CursorField> cursor) {
            Integer priceValue = cursor.get(ProductsCursorField.PRICE, Integer.class);
            Long productIdValue = cursor.get(ProductsCursorField.PRODUCTS_ID, Long.class);

            if (priceValue == null || productIdValue == null) {
                return Optional.empty();
            }

            return Optional.of(QProducts.products.price.lt(priceValue)
                    .or(QProducts.products.price.eq(priceValue)
                            .and(QProducts.products.productId.lt(productIdValue))));
        }
    };

    private final String key;
    private final List<ProductsCursorField> cursorFields;
    private final List<OrderSpecifier<?>> orderSpecifiers;

    ProductCursorStrategy(String key, List<ProductsCursorField> cursorFields, List<OrderSpecifier<?>> orderSpecifiers) {
        this.key = key;
        this.cursorFields = cursorFields;
        this.orderSpecifiers = orderSpecifiers;
    }

    public String getKey() {
        return key;
    }

    @Override
    public List<OrderSpecifier<?>> getOrderSpecifiers() {
        return orderSpecifiers;
    }

    public List<ProductsCursorField> getCursorFields() {
        return cursorFields;
    }

    public static ProductCursorStrategy getSearchSortKey(String key) {
        return Arrays.stream(values())
                .filter(k -> k.key.equalsIgnoreCase(key))
                .findFirst()
                .orElseThrow(() -> new NotFoundCursorSortStrategy());
    }

}
