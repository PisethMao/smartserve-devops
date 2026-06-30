package co.istad.smartserve.features.promotion.category.bridge;

import co.istad.smartserve.features.category.Category;
import co.istad.smartserve.features.promotion.Promotion;

public abstract class PromotionCategoryAssignment {
    protected final PromotionCategoryRuleImplementor implementor;
    protected PromotionCategoryAssignment(PromotionCategoryRuleImplementor implementor) {
        this.implementor = implementor;
    }
    public abstract void validateBeforeAttach(Promotion promotion, Category category);
}
