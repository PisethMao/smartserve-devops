package co.istad.smartserve.features.promotion.category.command;

import co.istad.smartserve.features.promotion.category.PromotionCategory;
import org.springframework.stereotype.Component;

@Component
public class PromotionCategoryCommandInvoker {
    public PromotionCategory execute(PromotionCategoryCommand command) {
        return command.execute();
    }
}
