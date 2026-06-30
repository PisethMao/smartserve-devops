package co.istad.smartserve.features.promotion.menuitem;

import co.istad.smartserve.features.promotion.menuitem.dto.PromotionMenuItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PromotionMenuItemMapper {
    @Mapping(source = "promotion.id", target = "promotionId")
    @Mapping(source = "menuItem.id", target = "menuItemId")
    @Mapping(source = "menuItem.nameEn", target = "menuItemNameEn")
    @Mapping(source = "menuItem.nameKh", target = "menuItemNameKh")
    @Mapping(source = "menuItem.price", target = "price")
    PromotionMenuItemResponse toResponse(PromotionMenuItem promotionMenuItem);
}
