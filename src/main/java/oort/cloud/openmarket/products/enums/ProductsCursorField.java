package oort.cloud.openmarket.products.enums;

import oort.cloud.openmarket.common.cusor.CursorField;
import oort.cloud.openmarket.products.controller.response.ProductsResponse;

import java.time.LocalDateTime;

public enum ProductsCursorField implements CursorField {
    SALES_AT("salesAt", LocalDateTime.class){
        @Override
        public Comparable<?> extract(ProductsResponse resp) {
            return resp.getSalesAt();
        }
    },
    PRODUCTS_ID("productsId", Long.class) {
        @Override
        public Comparable<?> extract(ProductsResponse resp) {
            return resp.getProductId();
        }
    },
    PRICE("price", Integer.class) {
        @Override
        public Comparable<?> extract(ProductsResponse resp) {
            return resp.getPrice();
        }
    };

    private final String fieldName;
    private final Class<? extends Comparable<?>> type;

    ProductsCursorField(String fieldName, Class<? extends Comparable<?>> type) {
        this.fieldName = fieldName;
        this.type = type;
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public Class<? extends Comparable<?>> getType() {return type;}
    public abstract Comparable<?> extract(ProductsResponse resp);
}
