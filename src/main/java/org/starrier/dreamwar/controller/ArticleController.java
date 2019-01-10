package org.starrier.dreamwar.controller;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.starrier.dreamwar.annotation.RateLimit;
import org.starrier.dreamwar.common.ResponseCode;
import org.starrier.dreamwar.entity.Article;
import org.starrier.dreamwar.enums.ExchangeEnum;
import org.starrier.dreamwar.enums.TopicEnum;
import org.starrier.dreamwar.repository.ArticleRepository;
import org.starrier.dreamwar.service.ArticleService;
import org.starrier.dreamwar.service.RabbitmqService;
import org.starrier.dreamwar.service.impl.ArticleServiceImpl;
import org.starrier.dreamwar.entity.Comment;
import rx.Observable;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * Article Controller.
 *
 *
 * @author  Starrier
 * @date 2018/10/27.
 */
@Api(value = "Article")
@RestController
@RequestMapping(value = "/article")
public class ArticleController {

    /**
     * LOGGER.
     * */
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleController.class);

    /**
     * articleRepository.
     * */
    private final ArticleRepository articleRepository;

    /**
     * articleService.
     * */
    private final ArticleService articleService;

    /**
     * rabbitmqService.
     * */
    private final RabbitmqService rabbitmqService;

    @Autowired
    public ArticleController(ArticleService articleService, RabbitmqService rabbitmqService, ArticleRepository articleRepository) {
        this.articleService = articleService;
        this.rabbitmqService = rabbitmqService;
        this.articleRepository = articleRepository;
    }

    /**
     * commentAddServiceClient.
     * */

    /**
     * <p>Add article </p>
     * <p>Insert Article with id</p>
     * @see ArticleServiceImpl#insertArticle(Article)
     * @param article Article, this is the real
     * @return return the result whether the method process success or fail or not.
     */
    @ApiOperation(value = "Post Article")
    @ApiImplicitParam(paramType = "path", name = "id", dataType = "Long", value = "article id", required = true)
    @ApiResponses({@ApiResponse(code = 200,message = ""),
                  @ApiResponse(code = 404 ,message = "")})
    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> insertArticleById(@RequestBody Article article)  {

        Date current_time = new Date();
        long longTime = current_time.getTime();
        Timestamp timestamp = new Timestamp(longTime);
        article.setCreate_date(timestamp);
        articleService.insertArticle(article);



        /**
         * 1.发送消息到rabbitmq服务端
         * 2.写入数据库
         * 3.异步消息，邮件通知
         */
        try {

            return new ResponseEntity<>("Article have been inserted!", HttpStatus.OK);
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("Article have got error:[{}]", e.getMessage());
            }
            return new ResponseEntity<>("Article inserted has been failed! !", HttpStatus.FAILED_DEPENDENCY);
        }

    }




    /**
     * <p>Post comment</p>
     *
     * 1.
     *
     * @param articleId  article's id
     * @param comment Comment detail content
     * @return return the result
     */
    public ResponseEntity<?> postComment(final @RequestParam("id") Long articleId, final Comment comment) {


        Optional<Article> articleOptional = articleRepository.findById(articleId);
        if (!articleOptional.isPresent()) {
            return new ResponseEntity<>("The corresponding article does not exist!", HttpStatus.BAD_REQUEST);
        } else {

            Article article = articleOptional.get();
            article.setComment_count(article.getComment_count() + 1);
            return new ResponseEntity<>("Comment has add", HttpStatus.OK);
        }
    }



    /**
     * <p> Delete  /p>
     * <p> Delete Article By ArticleId<</p>
     *  1. Determine whether the user is the current user or is the administrator
     *  2. User who meet the current appeal conditions can only,otherwise an exception is returned
     *  3. Check the current article''s id exists,if so,delete,otherwise an exception is retured
     *  4. Return the result with {@link ResponseEntity}
     *
     * @see ArticleRepository#deleteById(Object)
     * @param articleId delete by id
     * @return  status code. return 200 while operation have been done successful
     *                      ,and return error code depends on  exception.
     */
    @ApiOperation(value = "Delete Article", notes = "Delete Article By ArticleId")
    @DeleteMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    @CacheEvict(value = "articleId")
    @ResponseBody
    public ResponseEntity<?> deleteArticleById(final @PathVariable("id") Long articleId) {

        Optional<Article> articleOptional = articleRepository.findById(articleId);
        if (articleOptional.isPresent()) {
            LOGGER.info("Thr article would have been deleted has been checked out:[{}]", articleOptional);
            articleService.deleteById(articleId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        } else {


             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }




    /**
     * <p>Delete</p>
     * <p>Delete Article By Author</p>
     * 1. Determine
     * @see ArticleServiceImpl#deleteArticleByAuthor(String)
     * @param author which is article's own
     * @return status code. return 200 while operation have been done successful
     *                      ,and return error code depends on  exception.
     * */
    @ApiOperation(value = "Delete Article", notes = "Delete Article By Author")
    @CacheEvict(value = "authorArticles")
    @DeleteMapping(value = "/author/{author}",
            produces = "application/json",
            consumes = "application/json")
    public ResponseCode<Object> deleteArticleByAuthor(final @PathVariable("author") String author) {

        articleService.deleteArticleByAuthor(author);
        return ResponseCode.success(HttpStatus.OK);
    }

    /**
     * <p>update article for detail</p>
     * 1. Determine whether the current article that needs to
     *     be modified exist with article's di unique.{@link Optional},{@link ArticleRepository#findById(Object)}
     * 2. If the article exists,modify it and surround it with
     *    the try-catch statement
     * 3. return the result whether successful or not
     * @param article Deliver content that user needs to modify
     * @return {@link ResponseEntity}
     * @see ArticleServiceImpl#updateArticle(Article)
     * */
    @ApiOperation(value = "Update Article")
    @CachePut(value = "articleId")
    @ResponseBody
    @PutMapping(value = "/updateArticle", produces = "application/json", consumes = "application/json")
    public ResponseCode<Object> updateArticle(final @RequestBody Article article) {

        Optional<Article> articleOptional = articleRepository.findById(article.getId());
        if (!articleOptional.isPresent()) {
            LOGGER.info("Update  Article is  not exit:{}", article);
            return ResponseCode.error(HttpStatus.NOT_FOUND,"Update Article is not exist");
        }
        try {
            Date current_time = new Date();
            long longTime = current_time.getTime();
            Timestamp timestamp = new Timestamp(longTime);
            article.setUpdate_date(timestamp);
            articleService.updateArticle(article);

        } catch (Exception e) {
            LOGGER.error("An error occurred  in the process of updating the article:[{}]", e.toString());
        }
        return ResponseCode.success(200);
    }


    /**
     * <p>Fetch Article via article's id</p>
     *
     * @see ArticleServiceImpl#getArticleById(Long)
     * @param id which is article's identify
     * @throws Exception where you can find the more information in it
     * @return status code. return 200 while operation have been done successful
     *                    ,and return error code depends on  exception.
     */
    @ApiOperation(value = "Get article with id.")
    @Cacheable(value = "articleId")
    @GetMapping("/{id}")
    public ResponseCode getArticleById(final @PathVariable(value = "id") Long id) throws  Exception{

        Article articleOptional = articleService.getArticleById(id);
        LOGGER.info("Optional find the information:[{}]", articleOptional);

        return articleOptional!=null ?
                ResponseCode.success(articleOptional) :
                ResponseCode.error(HttpStatus.NOT_FOUND, "error");
    }



    /**
     * <p>Fetch</p>Find   :   Get All  Articles
     * <p>Get All Articles</p>
     * 1. List
     * @see ArticleServiceImpl#listArticle()
     * @return return the result {@link List}
     * */
    @ApiOperation(value = "Get  All Articles")
    @RateLimit(limitNum = 30,timeout = 50)
    @ResponseBody
    @GetMapping(produces = "application/json", consumes = "application/json")
    @Cacheable(value = "articles")
    public ResponseEntity<List<Article>> getAllArticles()  {

       /* CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitmqService.sendAndAck(ExchangeEnum.USER_REGISTER_TOPIC_EXCHANGE, TopicEnum.USER_REGISTER.getTopicRouteKey(), articleService.listArticle(), correlationData);
*/
        articleService.executeAsyn();

        return ResponseEntity.ok(articleService.listArticle());
    }

    /**
     * Fetch  Comment By Id With Feign.
     * @param id Long
     * @return return the result {@link ResponseCode}
     * */
    @ResponseBody
    @GetMapping(value = "{id}/comment")
    public ResponseCode<Object> getCommentById(final @PathVariable("id") Long id) {



        LOGGER.info("This  is  ArticleController  Find Comment with id:{}", id);

        return ResponseCode.success( );
    }

    /**
     * <p>Get Articles By Author</p>
     *  1.
     * @param author String
     * @throws Exception {@link Exception} to catch the exception information
     * @return return the result
     */
    @ResponseBody
    @GetMapping(value = "/author/{author}",
            consumes = "application/json",
            produces = "application/json")
    @Cacheable("authorArticles")
    @Transactional(rollbackOn = Exception.class)
    public List<Article> getArticleByAuthor(final @PathVariable("author") String author) throws Exception {
        List<Article> listByAuthor = articleService.getArticlesByAuthor(author);

        LOGGER.info("listByAuthor:{}", listByAuthor);
        return listByAuthor;
    }

    /**
     * <p>Find all the articles which include the word given</p>
     * 1.
     * @param keyword String
     * @return {@link ResponseCode}
     * */
    @GetMapping(value = "/search/{keyword}", consumes = "application/json", produces = "application/json")
    public ResponseCode<Object> search(final @PathVariable(value = "keyword") String keyword){
        Observable.just("hello world").subscribe(System.out::print);

        List<Article> articles = articleService.getArticlesByKeyword(keyword);

       return ResponseCode.success(articles);
    }

    /**
     * <p>Get All the article for the specified category with categoryId given</p>
     *1.
     * @param categoryId  article's category id
     * @return ResponseEntity<List<Article>>
     * */
    @RequestMapping("/category")
    public ResponseEntity<?> getArticleCategory(final @RequestParam(value = "category_id")int categoryId){
        Optional<List<Article>> articles = articleService.getArticlesByCategoryId(categoryId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(articles);

    }
}
