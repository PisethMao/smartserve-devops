package co.istad.smartserve.features.promotion.menuitem.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class PromotionMenuItemChangedEventListener {
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onPromotionMenuItemChanged(PromotionMenuItemChangedEvent event) {
        log.info(
                "Promotion menu item changed: promotionId={}, menuItemId={}, action={}",
                event.promotionId(),
                event.menuItemId(),
                event.action()
        );
    }
}
