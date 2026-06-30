package co.istad.smartserve.features.promotion.category.command;

import co.istad.smartserve.features.promotion.category.PromotionCategory;
import co.istad.smartserve.features.promotion.category.PromotionCategoryRepository;

public class DetachPromotionCategoryCommand implements PromotionCategoryCommand {
    private final PromotionCategoryRepository promotionCategoryRepository;
    private final PromotionCategory promotionCategory;

    public DetachPromotionCategoryCommand(
            PromotionCategoryRepository promotionCategoryRepository,
            PromotionCategory promotionCategory
    ) {
        this.promotionCategoryRepository = promotionCategoryRepository;
        this.promotionCategory = promotionCategory;
    }

    @Override
    public PromotionCategory execute() {
        promotionCategory.setDeleted(true);
        return promotionCategoryRepository.save(promotionCategory);
    }
}
