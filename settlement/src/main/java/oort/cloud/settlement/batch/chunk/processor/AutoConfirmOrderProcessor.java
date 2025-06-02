package oort.cloud.settlement.batch.chunk.processor;

import lombok.extern.slf4j.Slf4j;
import oort.cloud.settlement.batch.data.OrderItemDto;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AutoConfirmOrderProcessor implements ItemProcessor<OrderItemDto, OrderItemDto> {
    @Override
    public OrderItemDto process(OrderItemDto item) throws Exception {
        log.info("AutoConfirm OrderItem : {}", item);
        return item;
    }
}
