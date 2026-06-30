package co.istad.smartserve.features.promotion.factory;

import co.istad.smartserve.features.promotion.Promotion;
import co.istad.smartserve.features.promotion.dto.CreatePromotionRequest;
import co.istad.smartserve.features.restaurant.Restaurant;

public interface PromotionFactory {
    Promotion create(Restaurant restaurant, CreatePromotionRequest request);
}
