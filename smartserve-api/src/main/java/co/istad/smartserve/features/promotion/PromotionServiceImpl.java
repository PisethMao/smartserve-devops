package co.istad.smartserve.features.promotion;

import co.istad.smartserve.exception.ConflictException;
import co.istad.smartserve.exception.ResourceNotFoundException;
import co.istad.smartserve.features.category.Category;
import co.istad.smartserve.features.category.CategoryRepository;
import co.istad.smartserve.features.menuitem.MenuItem;
import co.istad.smartserve.features.menuitem.MenuItemRepository;
import co.istad.smartserve.features.promotion.command.ChangePromotionStatusCommand;
import co.istad.smartserve.features.promotion.command.CreatePromotionCommand;
import co.istad.smartserve.features.promotion.command.PromotionCommandInvoker;
import co.istad.smartserve.features.promotion.dto.CreatePromotionRequest;
import co.istad.smartserve.features.promotion.dto.PromotionResponse;
import co.istad.smartserve.features.promotion.dto.UpdatePromotionRequest;
import co.istad.smartserve.features.promotion.dto.UpdatePromotionStatusRequest;
import co.istad.smartserve.features.promotion.event.PromotionChangedEvent;
import co.istad.smartserve.features.promotion.factory.PromotionFactory;
import co.istad.smartserve.features.restaurant.Restaurant;
import co.istad.smartserve.features.restaurant.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PromotionServiceImpl implements PromotionService {
    private final PromotionRepository promotionRepository;
    private final RestaurantRepository restaurantRepository;
    private final CategoryRepository categoryRepository;
    private final MenuItemRepository menuItemRepository;
    private final PromotionMapper promotionMapper;
    private final PromotionFactory promotionFactory;
    private final PromotionCommandInvoker promotionCommandInvoker;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public PromotionResponse createPromotion(UUID restaurantId, CreatePromotionRequest request) {
        return promotionCommandInvoker.execute(
                new CreatePromotionCommand(this, restaurantId, request)
        );
    }

    private void validateDateRange(Instant startAt, Instant endAt) {
        if (startAt == null || endAt == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Promotion start date and end date are required"
            );
        }
        if (!endAt.isAfter(startAt)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Promotion end date must be after start date"
            );
        }
    }

    private void validateDiscount(PromotionDiscountType discountType, BigDecimal discountValue) {
        if (discountType == null || discountValue == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Discount type and discount value are required"
            );
        }
        if (discountValue.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Discount value must be greater than 0"
            );
        }
        if (discountType == PromotionDiscountType.PERCENTAGE
                && discountValue.compareTo(BigDecimal.valueOf(100)) > 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Percentage discount cannot be greater than 100"
            );
        }
    }

    private Set<UUID> normalizeIds(Set<UUID> ids) {
        if (ids == null) {
            return Collections.emptySet();
        }
        return ids.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private boolean hasValues(Set<UUID> ids) {
        return ids != null && !normalizeIds(ids).isEmpty();
    }

    private Set<Category> loadCategories(Restaurant restaurant, Set<UUID> categoryIds) {
        Set<Category> categories = new LinkedHashSet<>();
        for (UUID categoryId : categoryIds) {
            Category category = categoryRepository.findByIdAndDeletedFalse(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category has not been found"));
            if (!category.getRestaurant().getId().equals(restaurant.getId())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Category does not belong to this restaurant"
                );
            }
            categories.add(category);
        }
        return categories;
    }

    private Set<MenuItem> loadMenuItems(Restaurant restaurant, Set<UUID> menuItemIds) {
        Set<MenuItem> menuItems = new LinkedHashSet<>();
        for (UUID menuItemId : menuItemIds) {
            MenuItem menuItem = menuItemRepository.findByIdAndNotDeleted(menuItemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item has not been found"));
            if (!menuItem.getRestaurant().getId().equals(restaurant.getId())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Menu item does not belong to this restaurant"
                );
            }
            menuItems.add(menuItem);
        }
        return menuItems;
    }

    private void syncTargets(
            Promotion promotion,
            Restaurant restaurant,
            PromotionScope scope,
            Set<UUID> categoryIds,
            Set<UUID> menuItemIds,
            boolean createMode
    ) {
        if (scope == PromotionScope.ORDER) {
            if (hasValues(categoryIds) || hasValues(menuItemIds)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "ORDER promotion must not contain categoryIds or menuItemIds"
                );
            }
            promotion.getCategories().clear();
            promotion.getMenuItems().clear();
            promotion.setPromotionScope(PromotionScope.ORDER);
            return;
        }
        if (scope == PromotionScope.CATEGORY) {
            if (hasValues(menuItemIds)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "CATEGORY promotion must not contain menuItemIds"
                );
            }
            promotion.getMenuItems().clear();
            promotion.setPromotionScope(PromotionScope.CATEGORY);
            if (categoryIds != null) {
                Set<UUID> normalizedIds = normalizeIds(categoryIds);
                if (normalizedIds.isEmpty()) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "CATEGORY promotion requires at least one categoryId"
                    );
                }
                promotion.setCategories(loadCategories(restaurant, normalizedIds));
            } else if (createMode || promotion.getCategories().isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "CATEGORY promotion requires categoryIds"
                );
            }
            return;
        }
        if (scope == PromotionScope.MENU_ITEM) {
            if (hasValues(categoryIds)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "MENU_ITEM promotion must not contain categoryIds"
                );
            }
            promotion.getCategories().clear();
            promotion.setPromotionScope(PromotionScope.MENU_ITEM);
            if (menuItemIds != null) {
                Set<UUID> normalizedIds = normalizeIds(menuItemIds);
                if (normalizedIds.isEmpty()) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "MENU_ITEM promotion requires at least one menuItemId"
                    );
                }
                promotion.setMenuItems(loadMenuItems(restaurant, normalizedIds));
            } else if (createMode || promotion.getMenuItems().isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "MENU_ITEM promotion requires menuItemIds"
                );
            }
            return;
        }
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid promotion scope"
        );
    }

    private void publishEvent(Promotion promotion, String action) {
        applicationEventPublisher.publishEvent(
                new PromotionChangedEvent(
                        promotion.getId(),
                        promotion.getRestaurant().getId(),
                        action
                )
        );
    }

    private void validatePromotionDuplicate(Promotion promotion, UUID excludePromotionId) {
        if (!Boolean.TRUE.equals(promotion.getStatus())) {
            return;
        }
        UUID restaurantId = promotion.getRestaurant().getId();
        PromotionScope scope = promotion.getPromotionScope();
        Instant startAt = promotion.getStartAt();
        Instant endAt = promotion.getEndAt();
        if (scope == PromotionScope.ORDER) {
            boolean exists = promotionRepository.existsActiveOverlappingOrderPromotion(
                    restaurantId,
                    PromotionScope.ORDER,
                    startAt,
                    endAt,
                    excludePromotionId
            );
            if (exists) {
                throw new ConflictException(
                        "Active order promotion already exists for this restaurant and date range",
                        Map.of("promotionScope", "This restaurant already has an active overlapping ORDER promotion")
                );
            }
            return;
        }
        if (scope == PromotionScope.CATEGORY) {
            for (Category category : promotion.getCategories()) {
                boolean exists = promotionRepository.existsActiveOverlappingCategoryPromotion(
                        restaurantId,
                        category.getId(),
                        PromotionScope.CATEGORY,
                        startAt,
                        endAt,
                        excludePromotionId
                );
                if (exists) {
                    throw new ConflictException(
                            "Active category promotion already exists for this category and date range",
                            Map.of("categoryIds", "This category already has an active overlapping promotion")
                    );
                }
            }
            return;
        }
        if (scope == PromotionScope.MENU_ITEM) {
            for (MenuItem menuItem : promotion.getMenuItems()) {
                boolean exists = promotionRepository.existsActiveOverlappingMenuItemPromotion(
                        restaurantId,
                        menuItem.getId(),
                        PromotionScope.MENU_ITEM,
                        startAt,
                        endAt,
                        excludePromotionId
                );
                if (exists) {
                    throw new ConflictException(
                            "Active menu item promotion already exists for this menu item and date range",
                            Map.of("menuItemIds", "This menu item already has an active overlapping promotion")
                    );
                }
            }
        }
    }

    public PromotionResponse createPromotionInternal(UUID restaurantId, CreatePromotionRequest request) {
        Restaurant restaurant = restaurantRepository.findByIdAndDeletedFalse(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant has not been found"));
        validateDateRange(request.startAt(), request.endAt());
        validateDiscount(request.discountType(), request.discountValue());
        Promotion promotion = promotionFactory.create(restaurant, request);
        syncTargets(
                promotion,
                restaurant,
                request.promotionScope(),
                request.categoryIds(),
                request.menuItemIds(),
                true
        );
        validatePromotionDuplicate(promotion, null);
        promotion = promotionRepository.save(promotion);
        publishEvent(promotion, "CREATED");
        return promotionMapper.toPromotionResponse(promotion);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PromotionResponse> getPromotionsByRestaurant(UUID restaurantId, Pageable pageable) {
        return promotionRepository.findByRestaurant_IdAndDeletedFalse(restaurantId, pageable).map(promotionMapper::toPromotionResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionResponse> getActivePromotions(UUID restaurantId) {
        return promotionRepository
                .findActivePromotions(restaurantId, Instant.now())
                .stream()
                .map(promotionMapper::toPromotionResponse)
                .toList();
    }

    private Promotion findPromotionById(UUID promotionId) {
        return promotionRepository.findByIdAndDeletedFalse(promotionId)
                .orElseThrow(() -> new ResourceNotFoundException("Promotion has not been found"));
    }

    @Override
    @Transactional(readOnly = true)
    public PromotionResponse getPromotionById(UUID promotionId) {
        Promotion promotion = findPromotionById(promotionId);
        return promotionMapper.toPromotionResponse(promotion);
    }

    @Override
    public PromotionResponse updatePromotion(UUID promotionId, UpdatePromotionRequest request) {
        Promotion promotion = findPromotionById(promotionId);
        PromotionScope promotionScope = request.promotionScope() == null
                ? promotion.getPromotionScope()
                : request.promotionScope();
        Instant startAt = request.startAt() == null
                ? promotion.getStartAt()
                : request.startAt();
        Instant endAt = request.endAt() == null
                ? promotion.getEndAt()
                : request.endAt();
        PromotionDiscountType discountType = request.discountType() == null
                ? promotion.getDiscountType()
                : request.discountType();
        BigDecimal discountValue = request.discountValue() == null
                ? promotion.getDiscountValue()
                : request.discountValue();
        validateDateRange(startAt, endAt);
        validateDiscount(discountType, discountValue);
        promotionMapper.updateFromRequest(request, promotion);
        syncTargets(
                promotion,
                promotion.getRestaurant(),
                promotionScope,
                request.categoryIds(),
                request.menuItemIds(),
                false
        );
        validatePromotionDuplicate(promotion, promotionId);
        promotion = promotionRepository.save(promotion);
        publishEvent(promotion, "UPDATED");
        return promotionMapper.toPromotionResponse(promotion);
    }

    @Override
    public PromotionResponse changePromotionStatus(UUID promotionId, UpdatePromotionStatusRequest request) {
        return promotionCommandInvoker.execute(
                new ChangePromotionStatusCommand(this, promotionId, request.status()
                )
        );
    }

    public PromotionResponse changePromotionStatusInternal(UUID promotionId, Boolean status) {
        if (status == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Promotion status is required"
            );
        }
        Promotion promotion = findPromotionById(promotionId);
        if (status) {
            promotion.setStatus(true);
            validatePromotionDuplicate(promotion, promotionId);
        } else {
            promotion.setStatus(false);
        }
        promotion = promotionRepository.save(promotion);
        publishEvent(promotion, status ? "ACTIVATE" : "DEACTIVATE");
        return promotionMapper.toPromotionResponse(promotion);
    }

    @Override
    public void deletePromotion(UUID promotionId) {
        Promotion promotion = findPromotionById(promotionId);
        promotion.setDeleted(true);
        promotionRepository.save(promotion);
        publishEvent(promotion, "DELETED");
    }
}
