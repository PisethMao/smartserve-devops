package co.istad.smartserve.features.promotion.menuitem.factory;

import co.istad.smartserve.features.menuitem.MenuItem;
import co.istad.smartserve.features.promotion.Promotion;
import co.istad.smartserve.features.promotion.menuitem.PromotionMenuItem;

public interface PromotionMenuItemFactory {
    PromotionMenuItem create(Promotion promotion, MenuItem menuItem);
}
