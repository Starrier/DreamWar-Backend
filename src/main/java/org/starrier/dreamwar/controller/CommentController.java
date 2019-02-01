package org.starrier.dreamwar.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.starrier.dreamwar.common.Result;
import org.starrier.dreamwar.common.enums.ResultCode;
import org.starrier.dreamwar.entity.Comment;
import org.starrier.dreamwar.repository.CommentRepository;
import org.starrier.dreamwar.service.CommentService;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author Starrier
 * @Time 2018/10/27.
 */
@Api(value = "Comment")
@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

   /**
    *  <p>Post Comment with comment</p>
    *  @param comment article'comment
    * @param articleId  the article which wants to add comment
    * */
    @ResponseBody
    @PostMapping(produces = "application/json")
    @Transactional(rollbackOn = Exception.class)
    public Result addComment(@RequestBody Comment comment, Long articleId){

        Optional<Comment> commentOptional = commentRepository.findById(articleId);
        comment.setDate(new Date());

        LOGGER.info("Write Comment....");
        LOGGER.info("article_id:{},comment:{},date:{}",comment.getArticle_id(),comment.getComment(),comment.getDate());

        try {
            commentService.addComment(comment);
            try {
                commentService.sendEmail("你有新评论");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Result.success("Success");
        } catch (DuplicateKeyException exception) {
            LOGGER.error("Add  Comment Error:{}", exception);
            return Result.error(ResultCode.RESULE_DATA_NONE, exception.toString());
        }
    }

    /**
     * Add comment with article_id
     *
     * @param id
     */
    public void addComment(@PathVariable(value = "id") Long id) {

    }

    /**
     * Delete comment
     * @param id
     *
     * 1. 判断查询文章是否存在
     * 2. 判断当前 Dlete 操作用户是否为该文章的 Owner（管理员不具有改功能）
     * 3. 尝试操作，并返回结果（Success: 删除评论 fail ;  抛出操作失败的异常）
     */
    @ApiOperation(value = "Delete Comment By Id Which id is article's id")
    @ResponseBody
    @DeleteMapping(value = "/{id}")
    @CacheEvict(value = "comment",key = "#id")
    @Transactional(rollbackOn = Exception.class)
    public Result deleteCommentByArticleId(@PathVariable(value = "id") Long id,
                                                   @RequestParam(value = "article_id",required = false)Long article_id) {
        commentService.deleteById(id);
        return Result.success();
    }

    /**
     * Update comment
     *
     * @param comment
     */
    @ApiOperation(value = "Update Comment")
    @ResponseBody
    @PutMapping
    @Transactional(rollbackOn = Exception.class)
    public void updateComment(@RequestBody Comment comment) {

        LOGGER.info("Update ......");
        LOGGER.info("Update comment:{}", comment);
        commentService.updateComment(comment);

    }


    /**
     * 1.初始化 PageHelper
     * 2.实例化 要获取的结果，并封装到集合
     * 3.实例化要得到分页的结果对象
     * 4.得到分页中 comment 条目对象
     *
     * @param id
     * @param pageNum
     * @param pageSize
     *
     * @return
     * */
    @ApiOperation(value = "Get Comment By Id which id is article's id")
    @ResponseBody
    @GetMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    @Cacheable(value = "comment")
    @Transactional
    public Result showComment(@PathVariable(value = "id") Long id,
                                      @RequestParam(value = "pageNum", required = false, defaultValue = "3") int pageNum,
                                      @RequestParam(value = "pageSize", required = false, defaultValue = "3") int pageSize) {
        LOGGER.info("Get  Comment via id....");
        LOGGER.info("Comment id:{}", id);

        PageHelper.startPage(pageNum, pageSize);
        List<Comment> commentList = commentService.showComment(id);
        PageInfo<Comment> commentPageInfo = new PageInfo<>(commentList);
        List<Comment> pageList=commentPageInfo.getList();

        return Result.success(pageList);
    }

    @ResponseBody
    @GetMapping(value = "/article")
    public List<Comment> getCommentById(@RequestParam(value = "id") Long id) {


        return commentService.showComment(id);
    }
 }
