package co.istad.smartserve.features.promotion.command;

import co.istad.smartserve.features.promotion.PromotionServiceImpl;
import co.istad.smartserve.features.promotion.dto.CreatePromotionRequest;
import co.istad.smartserve.features.promotion.dto.PromotionResponse;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CreatePromotionCommand implements PromotionCommand {
    private final PromotionServiceImpl promotionService;
    private final UUID restaurantId;
    private final CreatePromotionRequest createPromotionRequest;

    @Override
    public PromotionResponse execute() {
        return promotionService.createPromotionInternal(restaurantId, createPromotionRequest);
    }
}
