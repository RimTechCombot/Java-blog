package com.example.demo.repositories;

import com.example.demo.domain.Upvote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
@Repository

public interface UpvoteRepository extends JpaRepository<Upvote, Long> {
     Upvote findByUser_idAndPage_id(Long user_id, Long page_id);
     Integer countByPage_id(Long page_id);
     @Transactional
     void deleteAllByPage_id(Long id);
}
