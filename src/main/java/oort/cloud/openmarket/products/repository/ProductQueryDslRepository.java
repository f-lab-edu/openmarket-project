package oort.cloud.openmarket.products.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import oort.cloud.openmarket.products.controller.response.ProductsResponse;
import oort.cloud.openmarket.products.entity.QProductCategory;
import oort.cloud.openmarket.products.entity.QProducts;
import oort.cloud.openmarket.products.enums.ProductsStatus;
import oort.cloud.openmarket.user.entity.QUsers;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ProductQueryDslRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<ProductsResponse> findByCategoryWithSortAndCursor(Long categoryId, BooleanExpression cursorCondition, List<OrderSpecifier<?>> orderSpecifiers, int size) {
        QProducts p = QProducts.products;
        QProductCategory pc = QProductCategory.productCategory;
        QUsers users = QUsers.users;

        return jpaQueryFactory
                .select(Projections.constructor(
                        ProductsResponse.class,
                        p.productId,
                        p.productName,
                        p.price,
                        p.stock,
                        p.salesAt,
                        users.userId,
                        users.userName
                ))
                .from(p)
                .join(p.productCategories, pc)
                .join(p.user, users)
                .where(
                        pc.category.categoryId.eq(categoryId),
                        p.status.in(ProductsStatus.SALE, ProductsStatus.OUT_OF_STOCK),
                        cursorCondition
                )
                .orderBy(orderSpecifiers.toArray(OrderSpecifier[]::new))
                .limit(size)
                .fetch();
    }
}
