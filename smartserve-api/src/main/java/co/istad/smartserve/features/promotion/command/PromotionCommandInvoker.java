package co.istad.smartserve.features.promotion.command;

import co.istad.smartserve.features.promotion.dto.PromotionResponse;
import org.springframework.stereotype.Component;

@Component
public class PromotionCommandInvoker {
    public PromotionResponse execute(PromotionCommand command) {
        return command.execute();
    }
}
