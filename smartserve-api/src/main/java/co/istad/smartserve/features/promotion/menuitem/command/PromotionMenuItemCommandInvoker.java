package co.istad.smartserve.features.promotion.menuitem.command;

import org.springframework.stereotype.Component;

@Component
public class PromotionMenuItemCommandInvoker {
    public <T> T execute(PromotionMenuItemCommand<T> command) {
        return command.execute();
    }
}
