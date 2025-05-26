package oort.cloud.settlement.batch.job;

import lombok.extern.slf4j.Slf4j;
import oort.cloud.settlement.batch.chunk.processor.SettlementProcessor;
import oort.cloud.settlement.batch.data.OrderItemDto;
import oort.cloud.settlement.batch.data.SettlementDto;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.Map;

/**
 *  실행 커맨드 : ./gradlew :settlement:bootRun --args="--job.name=jdbcSettlementJob confirmedAt=2025-05-20 status=COMPLETED run.id=$(date +%s)"
 *
 */

@Configuration
@Slf4j
public class JdbcSettlementJobConfig {
    private final PagingQueryProvider selectOrderItemQueryProvider;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final SettlementProcessor settlementProcessor;
    private final DataSource dataSource;

    public JdbcSettlementJobConfig(PagingQueryProvider selectOrderItemQueryProvider, JobRepository jobRepository, PlatformTransactionManager transactionManager, SettlementProcessor settlementProcessor, DataSource dataSource) {
        this.selectOrderItemQueryProvider = selectOrderItemQueryProvider;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.settlementProcessor = settlementProcessor;
        this.dataSource = dataSource;
    }

    @Bean
    public Job settlementJob(){
        return new JobBuilder("jdbcSettlementJob", jobRepository)
                .start(settlementStep())
                .build();
    }

    @Bean
    public Step settlementStep(){
        return new StepBuilder("jdbcSettlementStep", jobRepository)
                .<OrderItemDto, SettlementDto>chunk(2, transactionManager)
                .reader(settlementOrderItemReader(null, null))
                .processor(settlementProcessor)
                .writer(createSettlementWriter())
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {
                        log.info("jdbcSettlementStep 실행 중...");
                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        log.info("Read Count : {}", stepExecution.getReadCount());
                        log.info("write Count : {}", stepExecution.getWriteCount());
                        return ExitStatus.COMPLETED;
                    }
                })
                .build();
    }

    @Bean
    @StepScope
    public JdbcPagingItemReader<OrderItemDto> settlementOrderItemReader(
                                                        @Value("#{jobParameters[confirmedAt]}") LocalDate confirmedAtParam,
                                                        @Value("#{jobParameters[status]}") String status) {
        Map<String, Object> queryParam = Map.of("confirmedAt", confirmedAtParam, "status", status);
        return new JdbcPagingItemReaderBuilder<OrderItemDto>()
                .name("SettlementOrderItemReader")
                .dataSource(dataSource)
                .pageSize(10)
                .queryProvider(selectOrderItemQueryProvider)
                .parameterValues(queryParam)
                .beanRowMapper(OrderItemDto.class)
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<SettlementDto> createSettlementWriter() {
        return new JdbcBatchItemWriterBuilder<SettlementDto>()
                .dataSource(dataSource)
                .sql("""
                    INSERT INTO settlement
                    (user_id, order_item_id, settlement_amount, commission_amount, scheduled_at, paid_at, status, created_at)
                    VALUES(:userId, :orderItemId, :settlementAmount, :commissionAmount, :scheduledAt, :paidAt, :status, :createdAt);
                    """)
                .beanMapped()
                .build();
    }
}
