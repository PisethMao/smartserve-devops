package co.istad.smartserve.features.promotion.category.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PromotionCategoryChangedEventListener {
    @EventListener
    public void onPromotionCategoryChanged(PromotionCategoryChangedEvent event) {
        log.info(
                "Promotion category changed: action={}, promotionCategoryId={}, promotionId={}, categoryId={}",
                event.action(),
                event.promotionCategoryId(),
                event.promotionId(),
                event.categoryId()
        );
    }
}
