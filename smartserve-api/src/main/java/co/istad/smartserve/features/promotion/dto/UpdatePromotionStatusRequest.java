package co.istad.smartserve.features.promotion.dto;

import jakarta.validation.constraints.NotNull;

public record UpdatePromotionStatusRequest(
        @NotNull(message = "Status is required")
        Boolean status
) {
}
