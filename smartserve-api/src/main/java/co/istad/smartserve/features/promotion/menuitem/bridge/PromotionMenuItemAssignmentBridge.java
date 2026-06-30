package co.istad.smartserve.features.promotion.menuitem.bridge;

import co.istad.smartserve.features.menuitem.MenuItem;
import co.istad.smartserve.features.promotion.Promotion;
import org.springframework.stereotype.Component;

@Component
public class PromotionMenuItemAssignmentBridge extends PromotionMenuItemAssignment {
    public PromotionMenuItemAssignmentBridge(PromotionMenuItemRuleImplementor implementor) {
        super(implementor);
    }

    @Override
    public void validateBeforeAttach(Promotion promotion, MenuItem menuItem) {
        implementor.validate(promotion, menuItem);
    }
}
