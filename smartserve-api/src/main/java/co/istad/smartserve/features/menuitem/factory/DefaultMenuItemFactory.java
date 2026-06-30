package co.istad.smartserve.features.menuitem.factory;

import co.istad.smartserve.features.category.Category;
import co.istad.smartserve.features.menuitem.MenuItem;
import co.istad.smartserve.features.menuitem.MenuItemAvailabilityStatus;
import co.istad.smartserve.features.menuitem.dto.MenuItemCreateRequest;
import co.istad.smartserve.features.restaurant.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class DefaultMenuItemFactory implements MenuItemFactory {
    @Override
    public MenuItem createMenuItem(Restaurant restaurant, Category category, MenuItemCreateRequest createRequest) {
        return MenuItem.builder()
                .restaurant(restaurant)
                .category(category)
                .nameEn(createRequest.nameEn())
                .nameKh(createRequest.nameKh())
                .descriptionEn(createRequest.descriptionEn())
                .descriptionKh(createRequest.descriptionKh())
                .price(createRequest.price())
                .imageUrl(createRequest.imageUrl())
                .soldLimit(createRequest.soldLimit())
                .menuItemAvailabilityStatus(
                        createRequest.availabilityStatus() == null
                                ? MenuItemAvailabilityStatus.AVAILABLE
                                : createRequest.availabilityStatus()
                )
                .status(createRequest.status() == null || createRequest.status())
                .build();
    }
}
