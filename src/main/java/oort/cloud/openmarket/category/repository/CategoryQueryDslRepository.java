package oort.cloud.openmarket.category.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import oort.cloud.openmarket.category.controller.reponse.CategoryResponse;
import oort.cloud.openmarket.category.entity.QCategory;
import oort.cloud.openmarket.common.paging.offset.OffsetPageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class CategoryQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public CategoryQueryDslRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public OffsetPageResponse<CategoryResponse> findByParentIsNull(Long categoryId, Pageable pageable){
        QCategory category = QCategory.category;
        BooleanExpression condition = getCategoryCondition(categoryId);

        List<CategoryResponse> categoryList = jpaQueryFactory.select(
                        Projections.constructor(
                                CategoryResponse.class,
                                category.categoryId,
                                category.categoryName))
                .from(category)
                .where(condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount =
                Objects.requireNonNullElse(getTotalCount(category, condition), 0L);

        return new OffsetPageResponse<>(
                categoryList,
                pageable.getOffset(),
                pageable.getPageSize(),
                totalCount
                );
    }

    private Long getTotalCount(QCategory category, BooleanExpression condition) {
        return jpaQueryFactory.select(category.count())
                .from(category)
                .where(condition)
                .fetchOne();
    }

    private BooleanExpression getCategoryCondition(Long categoryId){
        QCategory category = QCategory.category;
        return categoryId == null ? category.parent.isNull() : category.parent.categoryId.eq(categoryId);
    }
}
