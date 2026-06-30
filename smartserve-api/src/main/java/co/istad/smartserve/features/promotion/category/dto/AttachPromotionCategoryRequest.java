package co.istad.smartserve.features.promotion.category.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AttachPromotionCategoryRequest(
        @NotNull(message = "Category ID is required")
        UUID categoryId
) {
}
