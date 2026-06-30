package co.istad.smartserve.features.menuitem.dto;

import co.istad.smartserve.features.menuitem.MenuItemAvailabilityStatus;
import jakarta.validation.constraints.NotNull;

public record MenuItemAvailabilityRequest(
        @NotNull(message = "Availability status is required")
        MenuItemAvailabilityStatus availabilityStatus
) {
}
