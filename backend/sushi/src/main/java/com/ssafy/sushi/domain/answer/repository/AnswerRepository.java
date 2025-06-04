package com.ssafy.sushi.domain.answer.repository;

import com.ssafy.sushi.domain.answer.dto.response.MyAnswerListResponse;
import com.ssafy.sushi.domain.answer.entity.Answer;
import com.ssafy.sushi.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    List<Answer> findBySushiId(Integer sushiId);

    @Query("SELECT new com.ssafy.sushi.domain.answer.dto.response.MyAnswerListResponse(" +
            "s.id, s.category.id, s.sushiType.id, s.title, s.content, a.isLiked, a.createdAt) " +
            "FROM Answer a " +
            "JOIN a.sushi s " +
            "WHERE a.user.id = :userId " +
            "AND (:keyword IS NULL OR s.title LIKE %:keyword% "+
            "OR s.content LIKE %:keyword%)")
    Page<MyAnswerListResponse> findMyAnswersByUserIdAndSearch(Integer userId, String keyword, Pageable pageable);

    @Query("SELECT a " +
            "FROM Answer a " +
            "JOIN FETCH a.sushi " +
            "WHERE a.user.id = :userId AND a.sushi.id = :sushiId")
    Optional<Answer> findMyAnswerDetail(Integer userId, Integer sushiId);

    boolean existsAnswerByUserIdAndSushiId(Integer user_id, Integer sushi_id);

    Integer user(User user);
}
