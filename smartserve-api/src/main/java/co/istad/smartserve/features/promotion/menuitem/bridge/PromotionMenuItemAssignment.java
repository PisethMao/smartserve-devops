package co.istad.smartserve.features.promotion.menuitem.bridge;

import co.istad.smartserve.features.menuitem.MenuItem;
import co.istad.smartserve.features.promotion.Promotion;

public abstract class PromotionMenuItemAssignment {
    protected final PromotionMenuItemRuleImplementor implementor;
    protected PromotionMenuItemAssignment(PromotionMenuItemRuleImplementor implementor) {
        this.implementor = implementor;
    }
    public abstract void validateBeforeAttach(Promotion promotion, MenuItem menuItem);
}
