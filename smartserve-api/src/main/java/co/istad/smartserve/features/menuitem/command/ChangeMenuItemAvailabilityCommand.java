package co.istad.smartserve.features.menuitem.command;

import co.istad.smartserve.features.menuitem.MenuItemService;
import co.istad.smartserve.features.menuitem.dto.MenuItemAvailabilityRequest;
import co.istad.smartserve.features.menuitem.dto.MenuItemResponse;

import java.util.UUID;

public record ChangeMenuItemAvailabilityCommand(UUID menuItemId, MenuItemAvailabilityRequest request) implements MenuItemCommand<MenuItemResponse> {
    @Override
    public MenuItemResponse execute(MenuItemService menuItemService) {
        return menuItemService.changeAvailability(menuItemId, request);
    }
}
