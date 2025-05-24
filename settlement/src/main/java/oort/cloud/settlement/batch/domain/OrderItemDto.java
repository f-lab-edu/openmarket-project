package oort.cloud.settlement.batch.domain;

import java.math.BigDecimal;
import java.util.Date;

public class OrderItemDto {
    private Long orderItemId;
    private Long userId;
    private Integer totalPrice;
    private BigDecimal commissionRate;
    private Date confirmedAt;


    public void setUserid(Long userid) {
        this.userId = userid;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setCommissionRate(BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
    }

    public void setConfirmedAt(Date confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public Long getUserid() {
        return userId;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public BigDecimal getCommissionRate() {
        return commissionRate;
    }

    public Date getConfirmedAt() {
        return confirmedAt;
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "orderItemId=" + orderItemId +
                ", totalPrice=" + totalPrice +
                ", commissionRate=" + commissionRate +
                ", confirmedAt=" + confirmedAt +
                '}';
    }
}
