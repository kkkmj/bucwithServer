package com.bucwith.dto.community;

import com.bucwith.domain.community.Category;
import com.bucwith.domain.community.Community;
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
