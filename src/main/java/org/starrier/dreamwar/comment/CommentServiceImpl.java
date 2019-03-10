package org.starrier.dreamwar.comment;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Starrier
 * @Time 2018/10/27.
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Resource
    CommentDao commentDao;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value(value = "commentexchange")
    private String exchage;

    @Value(value = "topic.#")
    private String routeKey;


    /**
     * 根据id获取 Comment
     *
     * @param id
     * @return
     */
/*    @Override
    public Optional<Comment> getCommentById(Long id) {
        return commentDao.findById(id);
    }*/

    @Override
    public void addComment(Comment comment) {
        commentDao.addComment(comment);
    }

    @Override
    public void deleteById(Long id) {
        commentDao.deleteById(id);
    }

    @Override
    public void updateComment(Comment comment) {
        commentDao.updateComment(comment);
    }

    @Override
    public List<Comment> showComment(Long id) {
        return commentDao.showComment(id);
    }

    /**
     * 发送邮件任务存入消息队列
     *
     * @param message
     * @throws Exception
     */
    @Async
    @Override
    public void sendEmail(String message) throws Exception {

        rabbitTemplate.convertAndSend(exchage, routeKey, message);
    }
}
