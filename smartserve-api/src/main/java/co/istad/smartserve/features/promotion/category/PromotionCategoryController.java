package co.istad.smartserve.features.promotion.category;

import co.istad.smartserve.features.promotion.category.dto.AttachPromotionCategoryRequest;
import co.istad.smartserve.features.promotion.category.dto.PromotionCategoryResponse;
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

import java.util.UUID;

@Tag(name = "Promotion Categories", description = "RESTful APIs for managing the relationship between promotions and menu categories. Supports attaching categories to promotions, retrieving assigned categories, and removing category assignments.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PromotionCategoryController {
    private final PromotionCategoryService promotionCategoryService;

    @Operation(summary = "Attach category to promotion")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/promotions/{promotionId}/categories")
    public PromotionCategoryResponse attachCategory(
            @PathVariable UUID promotionId,
            @Valid @RequestBody AttachPromotionCategoryRequest request
    ) {
        return promotionCategoryService.attachCategory(promotionId, request);
    }

    @Operation(
            summary = "Get categories by promotion",
            description = "Retrieve all categories attached to a specific promotion with pagination and sorting support."
    )
    @GetMapping("/promotions/{promotionId}/categories")
    public Page<PromotionCategoryResponse> getCategoriesByPromotion(
            @Parameter(
                    description = "Unique ID of the promotion used to retrieve attached categories",
                    required = true
            )
            @PathVariable UUID promotionId,
            @ParameterObject
            @PageableDefault(
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ) {
        return promotionCategoryService.getCategoriesByPromotion(promotionId, pageable);
    }

    @Operation(summary = "Detach category from promotion by promotion category ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/promotion-categories/{promotionCategoryId}")
    public void detachByPromotionCategoryId(
            @PathVariable UUID promotionCategoryId
    ) {
        promotionCategoryService.detachByPromotionCategoryId(promotionCategoryId);
    }

    @Operation(summary = "Detach category from promotion by promotion ID and category ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/promotions/{promotionId}/categories/{categoryId}")
    public void detachByPromotionIdAndCategoryId(
            @PathVariable UUID promotionId,
            @PathVariable UUID categoryId
    ) {
        promotionCategoryService.detachByPromotionIdAndCategoryId(promotionId, categoryId);
    }
}
