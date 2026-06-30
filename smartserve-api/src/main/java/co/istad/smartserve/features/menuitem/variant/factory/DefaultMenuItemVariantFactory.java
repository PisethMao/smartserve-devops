package co.istad.smartserve.features.menuitem.variant.factory;

import co.istad.smartserve.features.menuitem.MenuItem;
import co.istad.smartserve.features.menuitem.MenuItemAvailabilityStatus;
import co.istad.smartserve.features.menuitem.variant.MenuItemVariant;
import co.istad.smartserve.features.menuitem.variant.builder.FluentMenuItemVariantBuilder;
import co.istad.smartserve.features.menuitem.variant.dto.MenuItemVariantCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class DefaultMenuItemVariantFactory implements MenuItemVariantFactory {
    @Override
    public MenuItemVariant createMenuItemVariant(MenuItem menuItem, MenuItemVariantCreateRequest request) {
        return FluentMenuItemVariantBuilder.aFluentMenuItemVariant()
                .withMenuItem(menuItem)
                .withNameEn(request.nameEn())
                .withNameKh(request.nameKh())
                .withPrice(request.price())
                .withDisplayOrder(request.displayOrder())
                .withAvailabilityStatus(request.availabilityStatus() != null
                        ? request.availabilityStatus()
                        : MenuItemAvailabilityStatus.AVAILABLE)
                .withDefaultVariant(request.defaultVariant() != null && request.defaultVariant())
                .build();
    }
}
