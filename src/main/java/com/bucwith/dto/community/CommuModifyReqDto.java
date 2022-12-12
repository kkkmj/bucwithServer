package com.bucwith.dto.community;

import com.bucwith.domain.commuCategory.Category;
import com.bucwith.domain.community.CommuType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommuModifyReqDto {
    @NotBlank(message = "내용을 입력해주세요")
    private String content;
    private CommuType type;
    private List<Category> category;
    private int party;

    @Builder
    public CommuModifyReqDto(String content, CommuType type, List<Category> category, int party){
        this.content = content;
        this.type = type;
        this.category = category;
        this.party = party;
    }
}
