package oort.cloud.settlement.batch.job;

import oort.cloud.settlement.batch.chunk.processor.AutoConfirmOrderProcessor;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 *    배달완료인 주문상품 중 배달완료 날짜가 7일 지난 상품에 대해 자동으로 구매확정 상태 변경
 *    1. 배달완료 날짜가 7일이 지난 주문상품 조회
 *    2. 주문상품의 상태를 구매확정으로 변경
 */
@Configuration
public class AutoConfirmOrderJobConfig {
    public final JobRepository jobRepository;
    public final DataSource dataSource;
    public final PlatformTransactionManager transactionManager;
    public final AutoConfirmOrderProcessor autoConfirmOrderProcessor;

    public AutoConfirmOrderJobConfig(JobRepository jobRepository, DataSource dataSource,
                                     PlatformTransactionManager transactionManager,
                                     AutoConfirmOrderProcessor autoConfirmOrderProcessor) {
        this.jobRepository = jobRepository;
        this.dataSource = dataSource;
        this.transactionManager = transactionManager;
        this.autoConfirmOrderProcessor = autoConfirmOrderProcessor;
    }

//    @Bean
//    public Job autoConfirmOrderJob(){
//        return new JobBuilder("autoConfirmOrderJob", jobRepository)
//                .start()
//
//    }

//    @Bean
//    public Step autoConfirmOrderStep(){
//        return new StepBuilder("autoConfirmOrderStep", jobRepository)
//                .<OrderItemDto, SettlementDto>chunk(2, transactionManager)
//                .reader(settlementOrderItemReader(null, null))
//                .processor(autoConfirmOrderProcessor)
//                .writer(createSettlementWriter())
//                .listener(settlementStepListener)
//                .faultTolerant()
//                .retry(SQLTimeoutException.class)
//                .retryLimit(3)
//                .processorNonTransactional() // 이미 처리된 Item에 대해서는 캐시를 적용하여 다시 진행하지 않음
//                .listener(createSettlementRetryListener)
//                .backOffPolicy(new ExponentialBackOffPolicy(){{
//                    setInitialInterval(1000L);
//                    setMultiplier(3);
//                    setMaxInterval(10000L);
//                }})
//                .build();
//    }
//
//    @Bean
//    @StepScope
//    public JdbcPagingItemReader<OrderItemDto> deliveredOrderItemReader(
//            @Value("#{jobParameters[deliveredBeforeDate]}") int deliveredBeforeDate,
//            @Value("#{jobParameters[status]}") OrderItemStatus status) {
//        Map<String, Object> queryParam = Map.of("status", status, "targetDate", deliveredBeforeDate);
//        return new JdbcPagingItemReaderBuilder<OrderItemDto>()
//                .name("settlementOrderItemReader")
//                .dataSource(dataSource)
//                .pageSize(10)
//                .queryProvider(selectOrderItemQueryProvider)
//                .parameterValues(queryParam)
//                .beanRowMapper(OrderItemDto.class)
//                .build();
//    }
}
