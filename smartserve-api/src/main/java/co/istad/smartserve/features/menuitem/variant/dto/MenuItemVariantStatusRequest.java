package co.istad.smartserve.features.menuitem.variant.dto;

import co.istad.smartserve.features.menuitem.MenuItemAvailabilityStatus;
import jakarta.validation.constraints.NotNull;

public record MenuItemVariantStatusRequest(
        @NotNull(message = "Availability status is required")
        MenuItemAvailabilityStatus availabilityStatus
) {
}
