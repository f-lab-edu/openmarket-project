package oort.cloud.settlement.batch.domain.entity;

import jakarta.persistence.*;
import oort.cloud.settlement.batch.domain.enums.SettlementStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "settlement")
public class Settlement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "settlement_id")
    private Long settlementId;

    private Long userId;

    @Column(name = "order_item_id", nullable = false)
    private Long orderItemId;

    @Column(name = "settlement_amount", nullable = false)
    private int settlementAmount;

    @Column(name = "scheduled_at", nullable = false)
    private LocalDate scheduledAt;

    @Column(name = "paid_at")
    private LocalDate paidAt;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private SettlementStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    protected Settlement(){}

    public static Settlement of(Long userId, int settlementAmount, SettlementStatus status,
                                Long orderItemId, LocalDate scheduledAt, LocalDate paidAt){
        Settlement settlement = new Settlement();
        settlement.userId = userId;
        settlement.orderItemId = orderItemId;
        settlement.settlementAmount = settlementAmount;
        settlement.scheduledAt = scheduledAt;
        settlement.paidAt = paidAt;
        settlement.status = status;
        settlement.createdAt = LocalDateTime.now();
        return settlement;
    }

    public void setSettlementAmount(int settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public void setScheduledAt(LocalDate scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public void setPaidAt(LocalDate paidAt) {
        this.paidAt = paidAt;
    }

    public void setStatus(SettlementStatus status) {
        this.status = status;
    }

    public Long getSettlementId() {
        return settlementId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public int getSettlementAmount() {
        return settlementAmount;
    }

    public LocalDate getScheduledAt() {
        return scheduledAt;
    }

    public LocalDate getPaidAt() {
        return paidAt;
    }

    public SettlementStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Settlement that = (Settlement) o;
        return Objects.equals(settlementId, that.settlementId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(settlementId);
    }

}
