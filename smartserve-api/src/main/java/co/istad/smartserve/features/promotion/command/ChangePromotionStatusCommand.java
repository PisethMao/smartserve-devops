package co.istad.smartserve.features.promotion.command;

import co.istad.smartserve.features.promotion.PromotionServiceImpl;
import co.istad.smartserve.features.promotion.dto.PromotionResponse;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class ChangePromotionStatusCommand implements PromotionCommand {
    private final PromotionServiceImpl promotionService;
    private final UUID promotionId;
    private final Boolean status;

    @Override
    public PromotionResponse execute() {
        return promotionService.changePromotionStatusInternal(promotionId, status);
    }
}
