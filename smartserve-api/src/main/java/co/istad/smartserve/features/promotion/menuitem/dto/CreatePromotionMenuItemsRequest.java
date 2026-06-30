package co.istad.smartserve.features.promotion.menuitem.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreatePromotionMenuItemsRequest(
        @NotEmpty(message = "Menu item IDs are required")
        List<@NotNull(message = "Menu item ID is required") UUID> menuItemIds
) {
}
