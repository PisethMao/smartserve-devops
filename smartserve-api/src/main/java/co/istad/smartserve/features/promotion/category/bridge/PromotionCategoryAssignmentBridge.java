package co.istad.smartserve.features.promotion.category.bridge;

import co.istad.smartserve.features.category.Category;
import co.istad.smartserve.features.promotion.Promotion;
import org.springframework.stereotype.Component;

@Component
public class PromotionCategoryAssignmentBridge extends PromotionCategoryAssignment {
    public PromotionCategoryAssignmentBridge(PromotionCategoryRuleImplementor implementor) {
        super(implementor);
    }

    @Override
    public void validateBeforeAttach(Promotion promotion, Category category) {
        implementor.validate(promotion, category);
    }
}
