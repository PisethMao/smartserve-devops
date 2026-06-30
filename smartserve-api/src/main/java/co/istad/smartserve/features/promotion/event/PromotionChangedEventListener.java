package co.istad.smartserve.features.promotion.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PromotionChangedEventListener {
    @EventListener
    public void onPromotionChanged(PromotionChangedEvent event) {
        log.info(
                "PROMOTION EVENT: action={}, promotionId={}, restaurantId={}",
                event.action(),
                event.promotionId(),
                event.restaurantId()
        );
    }
}
