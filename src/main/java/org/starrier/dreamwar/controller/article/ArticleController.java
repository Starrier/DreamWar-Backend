package org.starrier.dreamwar.controller.article;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.starrier.dreamwar.model.entity.Article;
import org.starrier.dreamwar.model.entity.Comment;
import org.starrier.dreamwar.repository.repository.ArticleRepository;
import org.starrier.dreamwar.service.impl.ArticleServiceImpl;
import org.starrier.dreamwar.service.interfaces.ArticleService;
import org.starrier.dreamwar.service.interfaces.RabbitmqService;
import org.starrier.dreamwar.utils.annotation.RateLimit;
import org.starrier.dreamwar.utils.common.Result;
import org.starrier.dreamwar.utils.common.enums.ResultCode;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Article Controller.
 *
 * @author Starrier
 * @date 2018/10/27.
 */
@Api(value = "Article")
@RestController
@Slf4j
@RequestMapping(value = "/articles")
public class ArticleController {

    private final ArticleRepository articleRepository;

    private final ArticleService articleService;

    private final RabbitmqService rabbitmqService;

    @Autowired
    public ArticleController(ArticleService articleService, RabbitmqService rabbitmqService, ArticleRepository articleRepository) {
        this.articleService = articleService;
        this.rabbitmqService = rabbitmqService;
        this.articleRepository = articleRepository;
    }

    /**
     * <p>Add article </p>
     * <p>Insert Article with id</p>
     *
     * @param article Article, this is the real
     * @return return the result whether the method process success or fail or not.
     * @see ArticleServiceImpl#insertArticle(Article)
     */
    @ApiOperation(value = "Post Article")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", value = "article id", required = true)
    @ApiResponses({@ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 404, message = "")})
    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> insertArticleById(@RequestBody Article article) {

        /**
         * 1.发送消息到rabbitmq服务端
         * 2.写入数据库
         * 3.异步消息，邮件通知
         */
        try {
            articleService.insertArticle(article);
            return new ResponseEntity<>("Article have been inserted!", HttpStatus.OK);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Article have got error:[{}]", e.getMessage());
            }
            return new ResponseEntity<>("Article inserted has been failed! !", HttpStatus.FAILED_DEPENDENCY);
        }
    }

    /**
     * <p>Post comment</p>
     * <p>
     * 1.
     *
     * @param articleId article's id
     * @param comment   Comment detail content
     * @return return the result
     */
    @PostMapping(value = "/comment", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> postComment(final @RequestParam("id") Long articleId, final Comment comment) {
        Optional<Article> articleOptional = articleRepository.findById(articleId);
        if (!articleOptional.isPresent()) {
            return new ResponseEntity<>("The corresponding article does not exist!", HttpStatus.BAD_REQUEST);
        } else {
            Article article = articleOptional.get();
            article.setCommentCount(article.getCommentCount() + 1);
            return new ResponseEntity<>("Comment has add", HttpStatus.OK);
        }
    }


    /**
     * <p> Delete  /p>
     * <p> Delete Article By ArticleId<</p>
     * 1. Determine whether the user is the current user or is the administrator
     * 2. UserBO who meet the current appeal conditions can only,otherwise an exception is returned
     * 3. Check the current article''s id exists,if so,delete,otherwise an exception is retured
     * 4. Return the result with {@link ResponseEntity}
     *
     * @param articleId delete by id
     * @return status code. return 200 while operation have been done successful
     * ,and return error code depends on  exception.
     * @see ArticleRepository#deleteById(Object)
     */
    @ApiOperation(value = "Delete Article", notes = "Delete Article By ArticleId")
    @DeleteMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    @CacheEvict(value = "articleId")
    @ResponseBody
    public ResponseEntity<?> deleteArticleById(final @PathVariable("id") Long articleId) {
        Optional<Article> articleOptional = articleRepository.findById(articleId);
        if (articleOptional.isPresent()) {
            log.info("Thr article would have been deleted has been checked out:[{}]", articleOptional);
            articleService.deleteById(articleId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * <p>Delete</p>
     * <p>Delete Article By Author</p>
     * 1. Determine
     *
     * @param author which is article's own
     * @return status code. return 200 while operation have been done successful
     * ,and return error code depends on  exception.
     * @see ArticleServiceImpl#deleteArticleByAuthor(String)
     */
    @ApiOperation(value = "Delete Article", notes = "Delete Article By Author")
    @CacheEvict(value = "authorArticles")
    @DeleteMapping(value = "/author/{author}")
    public Result deleteArticleByAuthor(final @PathVariable("author") String author) {
        articleService.deleteArticleByAuthor(author);
        return Result.success(HttpStatus.OK);
    }

    /**
     * <p>update article for detail</p>
     * 1. Determine whether the current article that needs to
     * be modified exist with article's di unique.{@link Optional},{@link ArticleRepository#findById(Object)}
     * 2. If the article exists,modify it and surround it with
     * the try-catch statement
     * 3. return the result whether successful or not
     *
     * @param article Deliver content that user needs to modify
     * @return {@link ResponseEntity}
     * @see ArticleServiceImpl#updateArticle(Article)
     */
    @ApiOperation(value = "Update Article")
    @CachePut(value = "articleId")
    @ResponseBody
    @PutMapping(value = "/updateArticle", produces = "application/json", consumes = "application/json")
    public Result updateArticle(final @RequestBody Article article) {
        Optional<Article> articleOptional = articleRepository.findById(article.getId());
        if (!articleOptional.isPresent()) {
            log.info("Update  Article is  not exit:{}", article);
            return Result.error(ResultCode.RESULE_DATA_NONE, "Update Article is not exist");
        }
        long longTime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(longTime);
        article.setUpdateDate(timestamp);
        articleService.updateArticle(article);
        return Result.success(200);
    }

    /**
     * <p>Fetch Article via article's id</p>
     *
     * @param id which is article's identify
     * @return status code. return 200 while operation have been done successful
     * ,and return error code depends on  exception.
     * @throws Exception where you can find the more information in it
     * @see ArticleServiceImpl#getArticleById(Long)
     */
    @ApiOperation(value = "Get article with id.")
    @Cacheable(value = "articleId")
    @GetMapping("/{id}")
    @SneakyThrows(Exception.class)
    public Result getArticleById(final @PathVariable(value = "id") Long id) {

        Article articleOptional = articleService.getArticleById(id);
        log.info("Optional find the information:[{}]", articleOptional);
        return articleOptional != null ? Result.success(articleOptional) : Result.error(ResultCode.RESULE_DATA_NONE, "error");
    }

    /**
     * <p>Fetch</p>Find   :   Get All  Articles
     * <p>Get All Articles</p>
     * 1. List
     *
     * @return return the result {@link List}
     * @see ArticleServiceImpl#listArticle()
     */
    @ApiOperation(value = "Get  All Articles")
    @RateLimit(limitNum = 30, timeout = 50)
    @ResponseBody
    @GetMapping(produces = "application/json", consumes = "application/json")
    @Cacheable(value = "articles")
    public ResponseEntity<List<Article>> getAllArticles() {

        articleService.executeAsynchronous();

        return ResponseEntity.ok(articleService.listArticle());
    }

    /**
     * Fetch  Comment By Id With Feign.
     *
     * @param id Long
     * @return return the result {@link Result}
     */
    @ResponseBody
    @GetMapping(value = "{id}/comment")
    public Result getCommentById(final @PathVariable("id") Long id) {

        if (log.isInfoEnabled()) {
            log.info("This  is  ArticleController  Find Comment with id:{}", id);
        }


        return Result.success();
    }

    /**
     * <p>Get Articles By Author</p>
     * 1.
     *
     * @param author String
     * @return return the result
     * @throws Exception {@link Exception} to catch the exception information
     */
    @ResponseBody
    @GetMapping(value = "/author/{author}")
    @Cacheable("authorArticles")
    @Transactional(rollbackOn = Exception.class)
    public List<Article> getArticleByAuthor(final @PathVariable("author") String author) throws Exception {
        List<Article> listByAuthor = articleService.getArticlesByAuthor(author);
        log.info("listByAuthor:{}", listByAuthor);
        return listByAuthor;
    }

    /**
     * <p>Find all the articles which include the word given</p>
     * 1.
     *
     * @param keyword String
     * @return {@link Result}
     */
    @GetMapping(value = "/search/{keyword}", consumes = "application/json", produces = "application/json")
    public Result search(final @PathVariable(value = "keyword") String keyword) {
        List<Article> articles = articleService.getArticlesByKeyword(keyword);
        return Result.success(articles);
    }

    /**
     * <p>Get All the article for the specified category with categoryId given</p>
     * 1.
     *
     * @param categoryId article's category id
     * @return ResponseEntity<List < Article>>
     */
    @RequestMapping("/category")
    public ResponseEntity<?> getArticleCategory(final @RequestParam(value = "category_id") int categoryId) {
        Optional<List<Article>> articles = articleService.getArticlesByCategoryId(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(articles);
    }
}
