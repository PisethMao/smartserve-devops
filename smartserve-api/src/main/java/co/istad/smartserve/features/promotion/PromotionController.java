package co.istad.smartserve.features.promotion;

import co.istad.smartserve.features.promotion.dto.CreatePromotionRequest;
import co.istad.smartserve.features.promotion.dto.PromotionResponse;
import co.istad.smartserve.features.promotion.dto.UpdatePromotionRequest;
import co.istad.smartserve.features.promotion.dto.UpdatePromotionStatusRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(
        name = "Promotion Management",
        description = "Manage restaurant promotions, including item-based, category-based, and order-level discounts."
)
public class PromotionController {
    private final PromotionService promotionService;

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create promotion")
    @PostMapping("/restaurants/{restaurantId}/promotions")
    public PromotionResponse createPromotion(
            @PathVariable UUID restaurantId,
            @Valid @RequestBody CreatePromotionRequest request
    ) {
        return promotionService.createPromotion(restaurantId, request);
    }

    @Operation(
            summary = "Get promotions by restaurant",
            description = "Retrieve paginated promotions for a restaurant."
    )
    @GetMapping("/restaurants/{restaurantId}/promotions")
    public Page<PromotionResponse> getPromotionsByRestaurant(
            @Parameter(description = "Restaurant ID", required = true)
            @PathVariable UUID restaurantId,
            @ParameterObject
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return promotionService.getPromotionsByRestaurant(restaurantId, pageable);
    }

    @Operation(summary = "Get active promotions by restaurant")
    @GetMapping("/restaurants/{restaurantId}/promotions/active")
    public List<PromotionResponse> getActivePromotions(
            @PathVariable UUID restaurantId
    ) {
        return promotionService.getActivePromotions(restaurantId);
    }

    @Operation(summary = "Get promotion by ID")
    @GetMapping("/promotions/{promotionId}")
    public PromotionResponse getPromotionById(
            @PathVariable UUID promotionId
    ) {
        return promotionService.getPromotionById(promotionId);
    }

    @Operation(summary = "Update promotion")
    @PatchMapping("/promotions/{promotionId}")
    public PromotionResponse updatePromotion(
            @PathVariable UUID promotionId,
            @Valid @RequestBody UpdatePromotionRequest request
    ) {
        return promotionService.updatePromotion(promotionId, request);
    }

    @Operation(summary = "Change promotion status")
    @PatchMapping("/promotions/{promotionId}/status")
    public PromotionResponse changePromotionStatus(
            @PathVariable UUID promotionId,
            @Valid @RequestBody UpdatePromotionStatusRequest request
    ) {
        return promotionService.changePromotionStatus(promotionId, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete promotion")
    @DeleteMapping("/promotions/{promotionId}")
    public void deletePromotion(
            @PathVariable UUID promotionId
    ) {
        promotionService.deletePromotion(promotionId);
    }
}
