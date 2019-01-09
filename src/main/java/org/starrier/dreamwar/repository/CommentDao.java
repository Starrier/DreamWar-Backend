package org.starrier.dreamwar.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.starrier.dreamwar.entity.Comment;

import java.util.List;

/**
 * @Author Starrier
 * @Time 2018/10/27.
 */
@Mapper
public interface CommentDao {

    void addComment(Comment comment);

    void deleteById(@Param("id") Long id);

    void updateComment(Comment comment);

    List<Comment> showComment(Long id);
}
