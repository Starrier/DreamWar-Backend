package org.starrier.dreamwar.controller.comment;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.starrier.dreamwar.model.entity.Comment;
import org.starrier.dreamwar.repository.repository.CommentRepository;
import org.starrier.dreamwar.service.interfaces.CommentService;

import java.util.List;
import java.util.Optional;

/**
 * @author Starrier
 * @date 2018/10/27.
 */
@Api(value = "Comment")
@Slf4j
@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    private final CommentService commentService;

    private final CommentRepository commentRepository;

    public CommentController(CommentService commentService, CommentRepository commentRepository) {
        this.commentService = commentService;
        this.commentRepository = commentRepository;
    }

    /**
     * <p>Post Comment with comment</p>
     *
     * @param comment   article'comment
     * @param articleId the article which wants to add comment
     */
    @ApiOperation(value = "Add Comment")
    @ApiImplicitParams({@ApiImplicitParam(name = "articleId", dataType = "Long", paramType = "query", value = "article id", required = true),
            @ApiImplicitParam()})
    @ApiResponses({@ApiResponse(code = 200, message = "SUCCESS"), @ApiResponse(code = 404, message = "")})
    @SneakyThrows(Exception.class)
    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity addComment(@RequestBody Comment comment, Long articleId) {

        Optional.of(commentRepository.findById(articleId));
        try {
            commentService.addComment(comment);
            commentService.sendEmail("你有新评论");
            return ResponseEntity.ok("Success");
        } catch (DuplicateKeyException exception) {
            return (ResponseEntity) ResponseEntity.notFound();
        }
    }

    /**
     * Delete comment
     *
     * @param id 1. 判断查询文章是否存在
     *           2. 判断当前 Dlete 操作用户是否为该文章的 Owner（管理员不具有改功能）
     *           3. 尝试操作，并返回结果（Success: 删除评论 fail ;  抛出操作失败的异常）
     */
    @ApiOperation(value = "Delete Comment By Id Which id is article's id")
    @ResponseBody
    @DeleteMapping(value = "/{id}")
    @CacheEvict(value = "comment", key = "#id")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity deleteCommentByArticleId(@PathVariable(value = "id") Long id, @RequestParam(value = "article_id", required = false) Long articleId) {
        commentService.deleteById(id);
        return (ResponseEntity) ResponseEntity.ok();
    }

    /**
     * Update comment
     *
     * @param comment
     */
    @ApiOperation(value = "Update Comment")
    @ResponseBody
    @PutMapping
    @Transactional(rollbackFor = Exception.class)
    public void updateComment(@RequestBody Comment comment) {
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
     * @return
     */
    @ApiOperation(value = "Get Comment By Id which id is article's id")
    @ResponseBody
    @GetMapping(value = "/{id}")
    @Cacheable(value = "comment")
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public ResponseEntity showComment(@PathVariable(value = "id") Long id,
                                      @RequestParam(value = "pageNum", required = false, defaultValue = "3") int pageNum,
                                      @RequestParam(value = "pageSize", required = false, defaultValue = "3") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> commentList = commentService.showComment(id);
        PageInfo<Comment> commentPageInfo = new PageInfo<>(commentList);
        List<Comment> pageList = commentPageInfo.getList();
        return ResponseEntity.ok(pageList);
    }

    @ResponseBody
    @GetMapping(value = "/article")
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public List<Comment> getCommentById(@RequestParam(value = "id") Long id) {
        return commentService.showComment(id);
    }
}
