package co.istad.smartserve.features.promotion.menuitem;

import co.istad.smartserve.features.promotion.menuitem.dto.CreatePromotionMenuItemsRequest;
import co.istad.smartserve.features.promotion.menuitem.dto.PromotionMenuItemResponse;
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
        name = "Promotion Menu Items",
        description = "Manage menu items included in promotions"
)
public class PromotionMenuItemController {
    private final PromotionMenuItemService promotionMenuItemService;

    @Operation(summary = "Attach menu items to promotion")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/promotions/{promotionId}/menu-items")
    public List<PromotionMenuItemResponse> attachMenuItems(
            @PathVariable UUID promotionId,
            @Valid @RequestBody CreatePromotionMenuItemsRequest request
    ) {
        return promotionMenuItemService.attachMenuItems(promotionId, request);
    }

    @Operation(summary = "Get menu items by promotion")
    @GetMapping("/promotions/{promotionId}/menu-items")
    public Page<PromotionMenuItemResponse> getMenuItemsByPromotion(
            @Parameter(description = "Promotion ID", required = true)
            @PathVariable UUID promotionId,
            @ParameterObject
            @PageableDefault(
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {
        return promotionMenuItemService.getMenuItemsByPromotion(promotionId, pageable);
    }

    @Operation(summary = "Remove menu item from promotion")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/promotions/{promotionId}/menu-items/{menuItemId}")
    public void removeMenuItemFromPromotion(
            @PathVariable UUID promotionId,
            @PathVariable UUID menuItemId
    ) {
        promotionMenuItemService.removeMenuItemFromPromotion(promotionId, menuItemId);
    }
}
