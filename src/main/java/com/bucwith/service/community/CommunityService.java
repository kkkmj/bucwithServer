package com.bucwith.service.community;

import com.bucwith.domain.account.User;
import com.bucwith.domain.community.*;
import com.bucwith.dto.community.*;
import com.bucwith.repository.community.CommuCateRepository;
import com.bucwith.repository.community.CommunityRepository;
import com.bucwith.repository.community.ClikeRepository;
import com.bucwith.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final UserService userService;
    private final ClikeRepository clikeRepository;
    private final CommuCateRepository commuCateRepository;

    public Long commuSave(CommuSaveReqDto reqDto){
        Long commuId = communityRepository.save(reqDto.toEntity(userService.getUser(reqDto.getUserId()))).getCommuId();

        if(!CollectionUtils.isEmpty(reqDto.getCategory())){
            SaveCategory(commuId, reqDto.getCategory());
        }
        return commuId;
    }

    @Transactional(readOnly = true)
    public CommuResDto findCommuById(Long userId, Long commuId){
        Community entity  = getCommu(commuId);
        return new CommuResDto(entity, getCategory(entity), getLike(entity,userService.getUser(userId)));
    }

    @Transactional(readOnly = true)
    public List<CommuResDto> findCommuAllDesc(Long userId){
        List<Community> communities = communityRepository.findAllDesc();
        return communities.stream().map(community -> new CommuResDto(community,
                getCategory(community),getLike(community, userService.getUser(userId)))).collect(Collectors.toList());
    }

    @Transactional
    public Long modifyCommu(Long commuId, CommuModifyReqDto reqDto){
        Community community = getCommu(commuId);
        community.modify(reqDto.getContent(), reqDto.getType(), reqDto.getParty());

        List<Category> categorys = getCategory(community);
        if(!Objects.equals(categorys, reqDto.getCategory())){
            commuCateRepository.deleteAllInBatch(community.getCommuCates());
            SaveCategory(commuId, reqDto.getCategory());
        }

        return commuId;
    }

    @Transactional
    public void deleteCommu(Long commuId){
        communityRepository.deleteById(commuId);
    }

    @Transactional
    public void commuLike(Long commuId, Long userId){
        Optional<Clike> isLike = clikeRepository.findByCommunityAndUser(getCommu(commuId), userService.getUser(userId));

        //좋아요가 이미 있을 시 delete하고(void) 없으면 객체를 return하기 때문에 이게 최선인가..?
        isLike.ifPresentOrElse(
                clike -> {
                    clikeRepository.delete(clike);
                    communityRepository.updateMinusLike(commuId);
                },
                () -> {
                    Clike like = Clike.builder()
                            .community(getCommu(commuId))
                            .user(userService.getUser(userId))
                            .build();
                    clikeRepository.save(like);
                    communityRepository.updatePlusLike(commuId);
                }
        );
    }

    public Community getCommu(Long commuId){
        return communityRepository.findById(commuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + commuId));
    }

    public void SaveCategory(Long commuId, List<Category> categoryList){
        List<CommuCate> CommuCates = categoryList.stream().
                map(category -> CommuCate.builder()
                        .community(getCommu(commuId))
                        .category(category)
                        .build()).collect(Collectors.toList());
        commuCateRepository.saveAll(CommuCates);
    }

    public List<Category> getCategory(Community community){
        return commuCateRepository.findByCommunity(community).stream().map(CommuCate::getCategory).collect(Collectors.toList());
    }

    public int updateView(Long commuId){
        return communityRepository.updateView(commuId);
    }

    public Boolean getLike(Community community, User user){
        return clikeRepository.existsByCommunityAndUser(community, user);
    }
}
