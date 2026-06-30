package co.istad.smartserve.features.promotion.category.bridge;

import co.istad.smartserve.features.category.Category;
import co.istad.smartserve.features.promotion.Promotion;

public interface PromotionCategoryRuleImplementor {
    void validate(Promotion promotion, Category category);
}
