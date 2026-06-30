package co.istad.smartserve.features.menuitem.command;

import co.istad.smartserve.features.menuitem.MenuItemService;

public interface MenuItemCommand<R> {
    R execute(MenuItemService menuItemService);
}
