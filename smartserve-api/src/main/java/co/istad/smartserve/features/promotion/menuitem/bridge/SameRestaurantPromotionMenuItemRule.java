package co.istad.smartserve.features.promotion.menuitem.bridge;

import co.istad.smartserve.exception.ConflictException;
import co.istad.smartserve.features.menuitem.MenuItem;
import co.istad.smartserve.features.promotion.Promotion;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Component
public class SameRestaurantPromotionMenuItemRule implements PromotionMenuItemRuleImplementor {
    @Override
    public void validate(Promotion promotion, MenuItem menuItem) {
        UUID promotionRestaurantId = promotion.getRestaurant().getId();
        UUID menuItemRestaurantId = menuItem.getRestaurant().getId();
        if (!Objects.equals(promotionRestaurantId, menuItemRestaurantId)) {
            throw new ConflictException(
                    "Menu item does not belong to the promotion restaurant",
                    Map.of("menuItemId", "Menu item must belong to the same restaurant as the promotion")
            );
        }
    }
}
