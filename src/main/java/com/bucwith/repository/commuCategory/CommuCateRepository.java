package com.bucwith.repository.commuCategory;

import com.bucwith.domain.commuCategory.Category;
import com.bucwith.domain.commuCategory.CommuCate;
import com.bucwith.domain.community.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommuCateRepository extends JpaRepository<CommuCate, Long> {
    List<CommuCate> findByCommunity(Community community);

    @Query("SELECT DISTINCT c.community FROM CommuCate c WHERE c.category IN (:categorys) ORDER BY c.ccId DESC")
    List<Community> findByCategory(@Param("categorys") List<Category> categorys);

}
