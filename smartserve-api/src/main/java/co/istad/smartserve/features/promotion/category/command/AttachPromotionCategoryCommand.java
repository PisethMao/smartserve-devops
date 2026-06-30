package co.istad.smartserve.features.promotion.category.command;

import co.istad.smartserve.features.category.Category;
import co.istad.smartserve.features.promotion.Promotion;
import co.istad.smartserve.features.promotion.category.PromotionCategory;
import co.istad.smartserve.features.promotion.category.PromotionCategoryRepository;
import co.istad.smartserve.features.promotion.category.factory.PromotionCategoryFactory;

public class AttachPromotionCategoryCommand implements PromotionCategoryCommand {
    private final PromotionCategoryRepository promotionCategoryRepository;
    private final PromotionCategoryFactory promotionCategoryFactory;
    private final Promotion promotion;
    private final Category category;

    public AttachPromotionCategoryCommand(
            PromotionCategoryRepository promotionCategoryRepository,
            PromotionCategoryFactory promotionCategoryFactory,
            Promotion promotion,
            Category category
    ) {
        this.promotionCategoryRepository = promotionCategoryRepository;
        this.promotionCategoryFactory = promotionCategoryFactory;
        this.promotion = promotion;
        this.category = category;
    }

    @Override
    public PromotionCategory execute() {
        PromotionCategory promotionCategory = promotionCategoryFactory.create(promotion, category);
        return promotionCategoryRepository.save(promotionCategory);
    }
}
