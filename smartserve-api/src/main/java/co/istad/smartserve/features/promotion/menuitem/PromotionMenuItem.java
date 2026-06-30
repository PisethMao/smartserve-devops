package co.istad.smartserve.features.promotion.menuitem;

import co.istad.smartserve.common.domain.BaseEntity;
import co.istad.smartserve.features.menuitem.MenuItem;
import co.istad.smartserve.features.promotion.Promotion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
        name = "promotion_menu_items",
        indexes = {
                @Index(name = "idx_promotion_menu_items_promotion_id", columnList = "promotion_id"),
                @Index(name = "idx_promotion_menu_items_menu_item_id", columnList = "menu_item_id")
        }
)
public class PromotionMenuItem extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;
}
