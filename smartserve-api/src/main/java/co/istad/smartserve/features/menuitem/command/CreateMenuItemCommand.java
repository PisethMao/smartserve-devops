package co.istad.smartserve.features.menuitem.command;

import co.istad.smartserve.features.menuitem.MenuItemService;
import co.istad.smartserve.features.menuitem.dto.MenuItemCreateRequest;
import co.istad.smartserve.features.menuitem.dto.MenuItemResponse;

import java.util.UUID;

public record CreateMenuItemCommand(UUID restaurantId,
                                    MenuItemCreateRequest menuItemCreateRequest) implements MenuItemCommand<MenuItemResponse> {
    @Override
    public MenuItemResponse execute(MenuItemService menuItemService) {
        return menuItemService.create(restaurantId, menuItemCreateRequest);
    }
}
