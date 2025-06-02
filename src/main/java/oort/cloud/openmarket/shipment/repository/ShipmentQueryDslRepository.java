package oort.cloud.openmarket.shipment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import oort.cloud.openmarket.common.paging.offset.OffsetPageResponse;
import oort.cloud.openmarket.shipment.controller.request.ShipmentSearchRequest;
import oort.cloud.openmarket.shipment.controller.response.ShipmentResponse;
import oort.cloud.openmarket.shipment.entity.QShipment;
import oort.cloud.openmarket.shipment.enums.ShipmentStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Repository
public class ShipmentQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QShipment shipment = QShipment.shipment;

    public ShipmentQueryDslRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    /**
     *    판매자별 배송 내역 조회
     *    기본 날짜 범위 : 하루치 배송 목록
     *    기본 정렬 : 최근 배송 날짜
     */
    public OffsetPageResponse<ShipmentResponse> getShipmentList(
            Long userId, Pageable pageable, ShipmentSearchRequest request){
        List<ShipmentResponse> contents = jpaQueryFactory.select(
                        Projections.constructor(ShipmentResponse.class,
                                shipment.shipmentId,
                                shipment.user.userId,
                                shipment.orderItem.orderItemId,
                                shipment.trackingNumber,
                                shipment.deliveryCompany,
                                shipment.status,
                                shipment.shippedAt,
                                shipment.deliveredAt
                                )
                ).from(shipment)
                .where(
                        shipment.user.userId.eq(userId),
                        eqStatus(request.getStatus()),
                        afterShippedAt(request.getStartDay()),
                        beforeShippedAt(request.getEndDay())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = jpaQueryFactory.select(shipment.count())
                .from(shipment)
                .where(
                        shipment.user.userId.eq(userId),
                        eqStatus(request.getStatus()),
                        afterShippedAt(request.getStartDay()),
                        beforeShippedAt(request.getEndDay())
                ).fetchOne();

        totalCount = Objects.requireNonNullElse(totalCount, 0L);
        return new OffsetPageResponse<>(contents, pageable.getOffset(), pageable.getPageSize(), totalCount);
    }

    private BooleanExpression eqStatus(ShipmentStatus status) {
        return status != null ? shipment.status.eq(status) : null;
    }

    private BooleanExpression afterShippedAt(LocalDate from) {
        if(from == null){
            from = LocalDate.now();
        }
        LocalDateTime start = LocalDateTime.of(from, LocalTime.MIN);
        return shipment.shippedAt.goe(start);
    }

    private BooleanExpression beforeShippedAt(LocalDate to) {
        if(to == null){
            to = LocalDate.now();
        }
        LocalDateTime end = LocalDateTime.of(to, LocalTime.MAX);
        return shipment.shippedAt.loe(end);
    }
}
