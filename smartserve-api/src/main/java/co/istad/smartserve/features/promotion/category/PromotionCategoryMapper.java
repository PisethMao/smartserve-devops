package co.istad.smartserve.features.promotion.category;

import co.istad.smartserve.features.promotion.category.dto.PromotionCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PromotionCategoryMapper {
    @Mapping(source = "promotion.id", target = "promotionId")
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.nameEn", target = "categoryNameEn")
    @Mapping(source = "category.nameKh", target = "categoryNameKh")
    PromotionCategoryResponse toPromotionCategoryResponse(PromotionCategory promotionCategory);
}
