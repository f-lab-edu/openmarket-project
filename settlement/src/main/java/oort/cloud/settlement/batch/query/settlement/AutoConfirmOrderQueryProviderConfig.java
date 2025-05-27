package oort.cloud.settlement.batch.query.settlement;

import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class AutoConfirmOrderQueryProviderConfig {

    @Bean
    public PagingQueryProvider selectDeliveredOrderItemQueryProvider(DataSource dataSource) throws Exception {
        SqlPagingQueryProviderFactoryBean queryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();
        queryProviderFactoryBean.setDataSource(dataSource);
        queryProviderFactoryBean.setSelectClause("""
                SELECT 
                        oi.order_item_id
                        ,p.user_id AS userId
                        ,oi.total_price
                        ,c.commission_rate AS commissionRate
                        ,oi.confirmed_at
                """);
        queryProviderFactoryBean.setFromClause(
                """
                FROM order_item oi INNER JOIN product_categories pc ON oi.product_id = pc.product_id
                                   INNER JOIN categories c ON pc.category_id  = c.category_id
                                   INNER JOIN products p ON oi.product_id = p.product_id
                """);
        queryProviderFactoryBean.setWhereClause("""
                WHERE oi.status = :status
                AND oi.confirmed_at = :confirmedAt
                """);
        queryProviderFactoryBean.setSortKeys(Map.of("order_item_id", Order.ASCENDING));
        return queryProviderFactoryBean.getObject();
    }
}
