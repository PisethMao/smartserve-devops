package co.istad.smartserve.features.promotion.category.bridge;

import co.istad.smartserve.exception.ConflictException;
import co.istad.smartserve.features.category.Category;
import co.istad.smartserve.features.promotion.Promotion;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SameRestaurantPromotionCategoryRule implements PromotionCategoryRuleImplementor {
    @Override
    public void validate(Promotion promotion, Category category) {
        if (!Objects.equals(
                promotion.getRestaurant().getId(),
                category.getRestaurant().getId()
        )) {
            throw new ConflictException("Category must belong to the same restaurant as the promotion.");
        }
    }
}
