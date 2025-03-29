package com.nhom07.DAMH_LTUD.repository;

import com.nhom07.DAMH_LTUD.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
