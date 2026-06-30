package co.istad.smartserve.features.menuitem.variant.command;

import org.springframework.stereotype.Component;

@Component
public class MenuItemVariantCommandInvoker {
    public <R> R invoke(MenuItemVariantCommand<R> command) {
        return command.execute();
    }
}
