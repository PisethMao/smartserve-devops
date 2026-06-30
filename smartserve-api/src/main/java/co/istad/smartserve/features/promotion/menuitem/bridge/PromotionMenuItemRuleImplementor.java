package co.istad.smartserve.features.promotion.menuitem.bridge;

import co.istad.smartserve.features.menuitem.MenuItem;
import co.istad.smartserve.features.promotion.Promotion;

public interface PromotionMenuItemRuleImplementor {
    /*
    * Bridge pattern is used to cut between its own abstraction
    * with implementation that make each component is independent.
    *
    */
    void validate(Promotion promotion, MenuItem menuItem);
}
