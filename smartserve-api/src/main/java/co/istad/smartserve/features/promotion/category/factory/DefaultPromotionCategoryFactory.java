package co.istad.smartserve.features.promotion.category.factory;

import co.istad.smartserve.features.category.Category;
import co.istad.smartserve.features.promotion.Promotion;
import co.istad.smartserve.features.promotion.category.PromotionCategory;
import org.springframework.stereotype.Component;

@Component
public class DefaultPromotionCategoryFactory implements PromotionCategoryFactory {
    @Override
    public PromotionCategory create(Promotion promotion, Category category) {
        PromotionCategory promotionCategory = new PromotionCategory();
        promotionCategory.setPromotion(promotion);
        promotionCategory.setCategory(category);
        return promotionCategory;
    }
}
