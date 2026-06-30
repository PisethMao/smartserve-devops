package co.istad.smartserve.features.promotion.menuitem;

import co.istad.smartserve.exception.ConflictException;
import co.istad.smartserve.exception.ResourceNotFoundException;
import co.istad.smartserve.features.menuitem.MenuItem;
import co.istad.smartserve.features.menuitem.MenuItemRepository;
import co.istad.smartserve.features.promotion.Promotion;
import co.istad.smartserve.features.promotion.PromotionRepository;
import co.istad.smartserve.features.promotion.menuitem.bridge.PromotionMenuItemAssignmentBridge;
import co.istad.smartserve.features.promotion.menuitem.command.PromotionMenuItemCommandInvoker;
import co.istad.smartserve.features.promotion.menuitem.dto.CreatePromotionMenuItemsRequest;
import co.istad.smartserve.features.promotion.menuitem.dto.PromotionMenuItemResponse;
import co.istad.smartserve.features.promotion.menuitem.event.PromotionMenuItemChangedEvent;
import co.istad.smartserve.features.promotion.menuitem.factory.PromotionMenuItemFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class PromotionMenuItemServiceImpl implements PromotionMenuItemService {
    private final PromotionMenuItemRepository promotionMenuItemRepository;
    private final PromotionRepository promotionRepository;
    private final MenuItemRepository menuItemRepository;
    private final PromotionMenuItemMapper promotionMenuItemMapper;
    private final PromotionMenuItemFactory promotionMenuItemFactory;
    private final PromotionMenuItemAssignmentBridge promotionMenuItemAssignmentBridge;
    private final PromotionMenuItemCommandInvoker promotionMenuItemCommandInvoker;
    private final ApplicationEventPublisher applicationEventPublisher;

    private Promotion getPromotionById(UUID promotionId) {
        return promotionRepository
                .findByIdAndDeletedFalse(promotionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Promotion was not found"
                ));
    }

    private MenuItem getMenuItemById(UUID menuItemId) {
        return menuItemRepository
                .findByIdAndNotDeleted(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Menu item was not found"
                ));
    }

    private List<PromotionMenuItemResponse> attachMenuItemsUseCase(
            UUID promotionId,
            CreatePromotionMenuItemsRequest request
    ) {
        Promotion promotion = getPromotionById(promotionId);
        List<UUID> menuItemIds = request.menuItemIds();
        Set<UUID> uniqueMenuItemIds = new LinkedHashSet<>(menuItemIds);
        if (uniqueMenuItemIds.size() != menuItemIds.size()) {
            throw new ConflictException(
                    "Duplicate menu item IDs are not allowed",
                    Map.of("menuItemIds", "Duplicate menu item IDs are not allowed")
            );
        }
        List<PromotionMenuItem> savedPromotionMenuItems = new ArrayList<>();
        for (UUID menuItemId : uniqueMenuItemIds) {
            if (promotionMenuItemRepository.existsByPromotion_IdAndMenuItem_IdAndDeletedFalse(
                    promotionId,
                    menuItemId
            )) {
                throw new ConflictException(
                        "Menu item is already attached to this promotion",
                        Map.of("menuItemId", "Menu item is already attached to this promotion")
                );
            }
            MenuItem menuItem = getMenuItemById(menuItemId);
            promotionMenuItemAssignmentBridge.validateBeforeAttach(promotion, menuItem);
            PromotionMenuItem promotionMenuItem = promotionMenuItemFactory.create(
                    promotion,
                    menuItem
            );
            PromotionMenuItem saved = promotionMenuItemRepository.save(promotionMenuItem);
            savedPromotionMenuItems.add(saved);
            applicationEventPublisher.publishEvent(new PromotionMenuItemChangedEvent(
                    promotionId,
                    menuItemId,
                    "ATTACHED"
            ));
        }
        return savedPromotionMenuItems
                .stream()
                .map(promotionMenuItemMapper::toResponse)
                .toList();
    }

    @Override
    public List<PromotionMenuItemResponse> attachMenuItems(UUID promotionId, CreatePromotionMenuItemsRequest request) {
        return promotionMenuItemCommandInvoker.execute(
                () -> attachMenuItemsUseCase(promotionId, request)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PromotionMenuItemResponse> getMenuItemsByPromotion(UUID promotionId, Pageable pageable) {
        Promotion promotion = getPromotionById(promotionId);
        return promotionMenuItemRepository
                .findByPromotion_IdAndDeletedFalse(promotion.getId(), pageable)
                .map(promotionMenuItemMapper::toResponse);
    }

    private void removeMenuItemFromPromotionUseCase(UUID promotionId, UUID menuItemId) {
        PromotionMenuItem promotionMenuItem = promotionMenuItemRepository
                .findByPromotion_IdAndMenuItem_IdAndDeletedFalse(promotionId, menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Promotion menu item was not found"
                ));
        promotionMenuItem.setDeleted(true);
        promotionMenuItemRepository.save(promotionMenuItem);
        applicationEventPublisher.publishEvent(new PromotionMenuItemChangedEvent(
                promotionId,
                menuItemId,
                "REMOVED"
        ));
    }

    @Override
    @Transactional
    public void removeMenuItemFromPromotion(UUID promotionId, UUID menuItemId) {
        promotionMenuItemCommandInvoker.execute(
                () -> {
                    removeMenuItemFromPromotionUseCase(promotionId, menuItemId);
                    return null;
                }
        );
    }
}
