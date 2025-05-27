package oort.cloud.settlement.batch.listener.settlement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SettlementStepListener implements StepExecutionListener {
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("Read Count : {}", stepExecution.getReadCount());
        log.info("write Count : {}", stepExecution.getWriteCount());
        return ExitStatus.COMPLETED;
    }
}
