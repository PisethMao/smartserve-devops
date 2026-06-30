package co.istad.smartserve.features.promotion;

import co.istad.smartserve.common.domain.BaseEntity;
import co.istad.smartserve.features.category.Category;
import co.istad.smartserve.features.menuitem.MenuItem;
import co.istad.smartserve.features.restaurant.Restaurant;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "promotions")
public class Promotion extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
    @Column(name = "title_en", nullable = false)
    private String titleEn;
    @Column(name = "title_kh")
    private String titleKh;
    @Column(name = "description_en", columnDefinition = "TEXT")
    private String descriptionEn;
    @Column(name = "description_kh", columnDefinition = "TEXT")
    private String descriptionKh;
    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false, length = 20)
    private PromotionDiscountType discountType;
    @Column(name = "discount_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;
    @Enumerated(EnumType.STRING)
    @Column(name = "promotion_scope", nullable = false, length = 20)
    private PromotionScope promotionScope;
    @Column(name = "start_at", nullable = false)
    private Instant startAt;
    @Column(name = "end_at", nullable = false)
    private Instant endAt;
    @Builder.Default
    @Column(name = "status", nullable = false)
    private Boolean status = true;
    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "promotion_categories",
            joinColumns = @JoinColumn(name = "promotion_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();
    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "promotion_menu_items",
            joinColumns = @JoinColumn(name = "promotion_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_item_id")
    )
    private Set<MenuItem> menuItems = new HashSet<>();
}
