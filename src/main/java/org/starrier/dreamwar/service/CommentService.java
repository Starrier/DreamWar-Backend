package org.starrier.dreamwar.service;

import org.starrier.dreamwar.entity.Comment;

import java.util.List;

/**
 * @Author Starrier
 * @Time 2018/10/27.
 */
public interface CommentService {

    /**
     * 根据id获取 Comment
     * @param id
     * @return
     */
    void addComment(Comment comment);

    void deleteById(Long id);

    void updateComment(Comment comment);

    /**
     * get comments
     *
     * @param id
     * @return
     * */
    List<Comment> showComment(Long id);


    /**
     * 发送邮件任务存入消息队列
     * @param message
     * @throws Exception
     */
    void sendEmail(String message) throws Exception;
}
