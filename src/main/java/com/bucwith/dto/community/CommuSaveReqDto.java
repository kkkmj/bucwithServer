package com.bucwith.dto.community;

import com.bucwith.domain.account.User;
import com.bucwith.domain.community.Category;
import com.bucwith.domain.community.CommuType;
import com.bucwith.domain.community.Community;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommuSaveReqDto {

    private User user;
    @NotBlank(message = "내용을 입력해주세요")
    private String content;
    private CommuType type;
    private List<Category> category;
    private int party;

    @Builder
    public CommuSaveReqDto(User user, String content, CommuType type, List<Category> category, int party){
        this.user = user;
        this.content = content;
        this.type = type;
        this.category = category;
        this.party = party;
    }


    public Community toEntity(){
        return Community.builder()
                .user(user)
                .content(content)
                .type(type)
                .party(party)
                .build();
    }

}
