package co.istad.smartserve.features.menuitem.factory;

import co.istad.smartserve.features.category.Category;
import co.istad.smartserve.features.menuitem.MenuItem;
import co.istad.smartserve.features.menuitem.dto.MenuItemCreateRequest;
import co.istad.smartserve.features.restaurant.Restaurant;

public interface MenuItemFactory {
    MenuItem createMenuItem(
            Restaurant restaurant,
            Category category,
            MenuItemCreateRequest createRequest
    );
}
