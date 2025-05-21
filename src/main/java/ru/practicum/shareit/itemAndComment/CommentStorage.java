package ru.practicum.shareit.itemAndComment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.itemAndComment.model.Comment;

public interface CommentStorage extends JpaRepository<Comment, Long> {
}
