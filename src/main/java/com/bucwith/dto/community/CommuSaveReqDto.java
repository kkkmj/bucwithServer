package com.bucwith.dto.community;

import com.bucwith.domain.user.User;
import com.bucwith.domain.commuCategory.Category;
import com.bucwith.domain.community.CommuType;
import com.bucwith.domain.community.Community;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommuSaveReqDto {

    private Long userId;
    @NotBlank(message = "내용을 입력해주세요")
    private String content;
    private CommuType type;
    private List<Category> category;
    private int party;

    public Community toEntity(User user){
        return Community.builder()
                .user(user)
                .content(content)
                .type(type)
                .party(party)
                .build();
    }

}
