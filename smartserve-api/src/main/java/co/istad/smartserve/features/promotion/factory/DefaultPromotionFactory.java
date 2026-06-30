package co.istad.smartserve.features.promotion.factory;

import co.istad.smartserve.features.promotion.Promotion;
import co.istad.smartserve.features.promotion.dto.CreatePromotionRequest;
import co.istad.smartserve.features.restaurant.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class DefaultPromotionFactory implements PromotionFactory {
    @Override
    public Promotion create(Restaurant restaurant, CreatePromotionRequest request) {
        return Promotion.builder()
                .restaurant(restaurant)
                .titleEn(request.titleEn())
                .titleKh(request.titleKh())
                .descriptionEn(request.descriptionEn())
                .descriptionKh(request.descriptionKh())
                .discountType(request.discountType())
                .discountValue(request.discountValue())
                .promotionScope(request.promotionScope())
                .startAt(request.startAt())
                .endAt(request.endAt())
                .status(request.status() == null || request.status())
                .build();
    }
}
