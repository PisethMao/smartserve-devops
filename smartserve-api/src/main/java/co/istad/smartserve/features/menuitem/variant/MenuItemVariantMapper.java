package co.istad.smartserve.features.menuitem.variant;

import co.istad.smartserve.features.menuitem.variant.dto.MenuItemVariantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MenuItemVariantMapper {
    @Mapping(source = "menuItem.id", target = "menuItemId")
    MenuItemVariantResponse toResponse(MenuItemVariant menuItemVariant);
}
