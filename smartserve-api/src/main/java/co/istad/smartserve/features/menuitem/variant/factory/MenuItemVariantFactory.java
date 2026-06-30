package co.istad.smartserve.features.menuitem.variant.factory;

import co.istad.smartserve.features.menuitem.MenuItem;
import co.istad.smartserve.features.menuitem.variant.MenuItemVariant;
import co.istad.smartserve.features.menuitem.variant.dto.MenuItemVariantCreateRequest;

public interface MenuItemVariantFactory {
    MenuItemVariant createMenuItemVariant(MenuItem menuItem, MenuItemVariantCreateRequest request);
}
