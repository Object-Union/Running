package com.example.running.Repository;

import com.example.running.Bean.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository(value = "CommentRepository")
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Page<Comment> findCommentsByMomentId(Integer momentId, Pageable pageable);
}
