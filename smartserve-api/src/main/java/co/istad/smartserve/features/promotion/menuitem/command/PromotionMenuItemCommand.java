package co.istad.smartserve.features.promotion.menuitem.command;

@FunctionalInterface
public interface PromotionMenuItemCommand<T> {
    T execute();
}
