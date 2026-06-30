package co.istad.smartserve.features.promotion.category;

import co.istad.smartserve.common.domain.BaseEntity;
import co.istad.smartserve.features.category.Category;
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
        name = "promotion_categories",
        indexes = {
                @Index(name = "idx_promotion_categories_promotion_id", columnList = "promotion_id"),
                @Index(name = "idx_promotion_categories_category_id", columnList = "category_id")
        }
)
public class PromotionCategory extends BaseEntity {
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "promotion_id", nullable = false)
        private Promotion promotion;
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "category_id", nullable = false)
        private Category category;
}
