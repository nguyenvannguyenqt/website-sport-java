package com.nhom07.DAMH_LTUD.service;

import com.nhom07.DAMH_LTUD.NotFoundByIdException;
import com.nhom07.DAMH_LTUD.model.Comment;
import com.nhom07.DAMH_LTUD.model.ContactUser;
import com.nhom07.DAMH_LTUD.repository.CommentRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@NoArgsConstructor
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getListComments()
    {
        return commentRepository.findAll();
    }

    public Comment getById(Long id) throws NotFoundByIdException {
        Optional<Comment> optional = commentRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new NotFoundByIdException("Không tìm thấy bình luận với id: " + id); // Adjusted error message
    }

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new IllegalStateException("Không tìm thấy bình luận với id: " + id); // Adjusted error message
        }
        commentRepository.deleteById(id);
    }

    public void updateComment(@NotNull Comment comment) {
        Comment existingComment = commentRepository.findById(comment.getMaComment())
                .orElseThrow(() -> new IllegalStateException("Bình luận với id: " + comment.getMaComment() + " không tồn tại."));
        commentRepository.save(existingComment);
    }


}
