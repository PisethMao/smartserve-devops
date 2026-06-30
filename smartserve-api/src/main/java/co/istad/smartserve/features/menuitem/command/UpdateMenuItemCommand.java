package co.istad.smartserve.features.menuitem.command;

import co.istad.smartserve.features.menuitem.MenuItemService;
import co.istad.smartserve.features.menuitem.dto.MenuItemResponse;
import co.istad.smartserve.features.menuitem.dto.MenuItemUpdateRequest;

import java.util.UUID;

public record UpdateMenuItemCommand(UUID restaurantId,
                                    MenuItemUpdateRequest menuItemUpdateRequest) implements MenuItemCommand<MenuItemResponse> {
    @Override
    public MenuItemResponse execute(MenuItemService menuItemService) {
        return menuItemService.update(restaurantId, menuItemUpdateRequest);
    }
}
