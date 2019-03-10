package org.starrier.dreamwar.comment;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.starrier.dreamwar.comment.Comment;

import java.util.List;

/**
 * @Author Starrier
 * @Time 2018/10/27.
 */
@Mapper
public interface CommentDao {

    /**
     * add comment.
     *
     * @param comment
     **/
    void addComment(@Param("comment") Comment comment);

    /**
     * delete comment
     *
     * @param id
     * */
    void deleteById(@Param("id") Long id);

    /**
     * update comment
     *
     * @param comment
     * */
    void updateComment(@Param("comment") Comment comment);

    /**
     * get comment
     *
     * @param id
     * @return list
     * */
    List<Comment> showComment(@Param("id") Long id);
}
