package co.istad.smartserve.features.promotion.menuitem.factory;

import co.istad.smartserve.features.menuitem.MenuItem;
import co.istad.smartserve.features.promotion.Promotion;
import co.istad.smartserve.features.promotion.menuitem.PromotionMenuItem;
import org.springframework.stereotype.Component;

@Component
public class DefaultPromotionMenuItemFactory implements PromotionMenuItemFactory {
    @Override
    public PromotionMenuItem create(Promotion promotion, MenuItem menuItem) {
        PromotionMenuItem promotionMenuItem = new PromotionMenuItem();
        promotionMenuItem.setPromotion(promotion);
        promotionMenuItem.setMenuItem(menuItem);
        return promotionMenuItem;
    }
}
