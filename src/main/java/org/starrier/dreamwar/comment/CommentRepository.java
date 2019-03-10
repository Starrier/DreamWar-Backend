package org.starrier.dreamwar.comment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.starrier.dreamwar.comment.Comment;

/**
 * @author Starrier
 * @date 2018/12/20.
 */
@Repository
public interface CommentRepository extends CrudRepository<Comment,Long> {
}
