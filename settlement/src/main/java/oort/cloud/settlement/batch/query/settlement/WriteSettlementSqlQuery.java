package oort.cloud.settlement.batch.query.settlement;

public enum WriteSettlementSqlQuery {
    INSERT("""
            INSERT INTO settlement
            (user_id, order_item_id, settlement_amount, commission_amount, scheduled_at, paid_at, status, created_at)
            VALUES
            (:userId, :orderItemId, :settlementAmount, :commissionAmount, :scheduledAt, :paidAt, :status, :createdAt);
            """);

    private final String query;

    WriteSettlementSqlQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
