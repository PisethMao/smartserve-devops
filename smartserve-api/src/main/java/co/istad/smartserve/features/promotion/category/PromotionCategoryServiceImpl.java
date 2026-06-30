package co.istad.smartserve.features.promotion.category;

import co.istad.smartserve.exception.ConflictException;
import co.istad.smartserve.exception.ResourceNotFoundException;
import co.istad.smartserve.features.category.Category;
import co.istad.smartserve.features.category.CategoryRepository;
import co.istad.smartserve.features.promotion.Promotion;
import co.istad.smartserve.features.promotion.PromotionRepository;
import co.istad.smartserve.features.promotion.category.bridge.PromotionCategoryAssignmentBridge;
import co.istad.smartserve.features.promotion.category.command.AttachPromotionCategoryCommand;
import co.istad.smartserve.features.promotion.category.command.DetachPromotionCategoryCommand;
import co.istad.smartserve.features.promotion.category.command.PromotionCategoryCommandInvoker;
import co.istad.smartserve.features.promotion.category.dto.AttachPromotionCategoryRequest;
import co.istad.smartserve.features.promotion.category.dto.PromotionCategoryResponse;
import co.istad.smartserve.features.promotion.category.event.PromotionCategoryChangedEvent;
import co.istad.smartserve.features.promotion.category.factory.PromotionCategoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PromotionCategoryServiceImpl implements PromotionCategoryService {
    private final PromotionRepository promotionRepository;
    private final CategoryRepository categoryRepository;
    private final PromotionCategoryRepository promotionCategoryRepository;
    private final PromotionCategoryMapper promotionCategoryMapper;
    private final PromotionCategoryFactory promotionCategoryFactory;
    private final PromotionCategoryCommandInvoker promotionCategoryCommandInvoker;
    private final PromotionCategoryAssignmentBridge promotionCategoryAssignmentBridge;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public PromotionCategoryResponse attachCategory(UUID promotionId, AttachPromotionCategoryRequest request) {
        Promotion promotion = promotionRepository.findByIdAndDeletedFalse(promotionId)
                .orElseThrow(() -> new RuntimeException("Promotion has not been found"));
        Category category = categoryRepository.findByIdAndDeletedFalse(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category has not been found"));
        promotionCategoryAssignmentBridge.validateBeforeAttach(promotion, category);
        if (promotionCategoryRepository.existsByPromotion_IdAndCategory_IdAndDeletedFalse(promotionId, request.categoryId())) {
            throw new ConflictException("Promotion has been attached to the category");
        }
        PromotionCategory promotionCategory = promotionCategoryCommandInvoker.execute(
                new AttachPromotionCategoryCommand(
                        promotionCategoryRepository,
                        promotionCategoryFactory,
                        promotion,
                        category
                )
        );
        eventPublisher.publishEvent(
                new PromotionCategoryChangedEvent(
                        promotionCategory.getId(),
                        promotion.getId(),
                        category.getId(),
                        "ATTACHED"
                )
        );
        return promotionCategoryMapper.toPromotionCategoryResponse(promotionCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PromotionCategoryResponse> getCategoriesByPromotion(UUID promotionId, Pageable pageable) {
        if (!promotionRepository.existsById(promotionId)) {
            throw new ResourceNotFoundException("Promotion has not been found");
        }
        return promotionCategoryRepository
                .findByPromotion_IdAndDeletedFalse(promotionId, pageable)
                .map(promotionCategoryMapper::toPromotionCategoryResponse);
    }

    @Override
    public void detachByPromotionCategoryId(UUID promotionCategoryId) {
        PromotionCategory promotionCategory = promotionCategoryRepository
                .findByIdAndDeletedFalse(promotionCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Promotion has not been found"));
        PromotionCategory detached = promotionCategoryCommandInvoker.execute(
                new DetachPromotionCategoryCommand(promotionCategoryRepository, promotionCategory)
        );
        eventPublisher.publishEvent(
                new PromotionCategoryChangedEvent(
                        detached.getId(),
                        detached.getPromotion().getId(),
                        detached.getCategory().getId(),
                        "DETACHED"
                )
        );
    }

    @Override
    public void detachByPromotionIdAndCategoryId(UUID promotionId, UUID categoryId) {
        PromotionCategory promotionCategory = promotionCategoryRepository
                .findByPromotion_IdAndCategory_IdAndDeletedFalse(promotionId, categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Promotion category has not been found."));
        PromotionCategory detached = promotionCategoryCommandInvoker.execute(
                new DetachPromotionCategoryCommand(
                        promotionCategoryRepository,
                        promotionCategory
                )
        );
        eventPublisher.publishEvent(new PromotionCategoryChangedEvent(
                detached.getId(),
                promotionId,
                categoryId,
                "DETACHED"
        ));
    }
}
