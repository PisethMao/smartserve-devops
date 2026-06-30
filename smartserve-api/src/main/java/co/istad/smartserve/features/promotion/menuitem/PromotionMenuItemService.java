package co.istad.smartserve.features.promotion.menuitem;

import co.istad.smartserve.features.promotion.menuitem.dto.CreatePromotionMenuItemsRequest;
import co.istad.smartserve.features.promotion.menuitem.dto.PromotionMenuItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PromotionMenuItemService {
    List<PromotionMenuItemResponse> attachMenuItems(
            UUID promotionId,
            CreatePromotionMenuItemsRequest request
    );

    Page<PromotionMenuItemResponse> getMenuItemsByPromotion(
            UUID promotionId,
            Pageable pageable
    );

    void removeMenuItemFromPromotion(
            UUID promotionId,
            UUID menuItemId
    );
}
