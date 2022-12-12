package com.bucwith.dto.commuCategory;

import com.bucwith.domain.commuCategory.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategorySaveReqDto {

    private Long commuId;

    private Category category;

    @Builder
    public CategorySaveReqDto(Long commuId, Category category){
        this.commuId = commuId;
        this.category = category;
    }


}
