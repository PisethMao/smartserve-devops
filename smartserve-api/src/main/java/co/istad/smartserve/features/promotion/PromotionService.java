package co.istad.smartserve.features.promotion;

import co.istad.smartserve.features.promotion.dto.CreatePromotionRequest;
import co.istad.smartserve.features.promotion.dto.PromotionResponse;
import co.istad.smartserve.features.promotion.dto.UpdatePromotionRequest;
import co.istad.smartserve.features.promotion.dto.UpdatePromotionStatusRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PromotionService {
    PromotionResponse createPromotion(UUID restaurantId, CreatePromotionRequest request);

    Page<PromotionResponse> getPromotionsByRestaurant(UUID restaurantId, Pageable pageable);

    List<PromotionResponse> getActivePromotions(UUID restaurantId);

    PromotionResponse getPromotionById(UUID promotionId);

    PromotionResponse updatePromotion(UUID promotionId, UpdatePromotionRequest request);

    PromotionResponse changePromotionStatus(UUID promotionId, UpdatePromotionStatusRequest request);

    void deletePromotion(UUID promotionId);
}
