package oort.cloud.settlement.batch.domain;

import oort.cloud.settlement.batch.domain.enums.SettlementStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SettlementDto {
    private Long orderItemId;
    private Long userId;
    private int settlementAmount;
    private int commissionAmount;
    private LocalDate scheduledAt;
    private LocalDate paidAt;
    private SettlementStatus status;
    private LocalDateTime createdAt;
    protected SettlementDto(){}

    public static SettlementDto of(Long userId, int settlementAmount, int commissionAmount, SettlementStatus status,
                                Long orderItemId, LocalDate scheduledAt, LocalDate paidAt){
        SettlementDto settlement = new SettlementDto();
        settlement.userId = userId;
        settlement.orderItemId = orderItemId;
        settlement.settlementAmount = settlementAmount;
        settlement.commissionAmount = commissionAmount;
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

    public void setCommissionAmount(int commissionAmount) {
        this.commissionAmount = commissionAmount;
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

    public String getStatus() {
        return status.name();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getCommissionAmount() {
        return commissionAmount;
    }
}
