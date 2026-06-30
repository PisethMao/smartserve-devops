package co.istad.smartserve.features.promotion.category.factory;

import co.istad.smartserve.features.category.Category;
import co.istad.smartserve.features.promotion.Promotion;
import co.istad.smartserve.features.promotion.category.PromotionCategory;

public interface PromotionCategoryFactory {
    PromotionCategory create(Promotion promotion, Category category);
}
