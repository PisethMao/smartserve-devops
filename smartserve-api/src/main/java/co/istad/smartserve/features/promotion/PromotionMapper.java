package co.istad.smartserve.features.promotion;

import co.istad.smartserve.features.category.Category;
import co.istad.smartserve.features.menuitem.MenuItem;
import co.istad.smartserve.features.promotion.dto.PromotionResponse;
import co.istad.smartserve.features.promotion.dto.UpdatePromotionRequest;
import org.mapstruct.*;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PromotionMapper {
    @Mapping(source = "restaurant.id", target = "restaurantId")
    @Mapping(target = "categoryIds", expression = "java(toCategoryIds(promotion.getCategories()))")
    @Mapping(target = "menuItemIds", expression = "java(toMenuItemIds(promotion.getMenuItems()))")
    PromotionResponse toPromotionResponse(Promotion promotion);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "menuItems", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromRequest(
            UpdatePromotionRequest request,
            @MappingTarget Promotion promotion
    );

    default Set<UUID> toCategoryIds(Set<Category> categories) {
        if (categories == null) {
            return Collections.emptySet();
        }
        return categories.stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
    }

    default Set<UUID> toMenuItemIds(Set<MenuItem> menuItems) {
        if (menuItems == null) {
            return Collections.emptySet();
        }
        return menuItems.stream()
                .map(MenuItem::getId)
                .collect(Collectors.toSet());
    }
}
