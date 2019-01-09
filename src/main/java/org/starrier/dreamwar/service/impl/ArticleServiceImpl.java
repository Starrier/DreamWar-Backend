package org.starrier.dreamwar.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.starrier.dreamwar.entity.Article;
import org.starrier.dreamwar.repository.ArticleDao;
import org.starrier.dreamwar.entity.Comment;
import org.starrier.dreamwar.service.ArticleService;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Collectors;


/**
 * <p>Article Service</p>
 *
 * @author  Starrier
 * @date   2018/10/27.
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");


    private final ArticleDao articleDao;

    @Autowired
    public ArticleServiceImpl(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }


    /**
     * <p>Insert Article with Id</p>
     * @param article
     *
     */
    @HystrixCommand
    @Transactional(rollbackOn = Exception.class)
    @Override
    public void insertArticle(Article article) {

        LOGGER.info(" ThreadContextService, Current thread:[{}]", Thread.currentThread().getId());
        articleDao.insertArticle(article);
    }


    @CacheResult
    @Override
    public List<Article> listArticle() {
        return articleDao.listArticle();
    }

    /**
     * Fetch   ：   Fetch Comment By Article id
     *
     * @param id Long
     */
    @Override
    public List<Comment> getCommentByArticle(Long id) {

        return articleDao.getCommentByArticle(id);
    }

    /**
     * 点赞
     *
     * @param articleId
     * @return
     */
    @Override
    public Article createVote(Long articleId) {
        Article optionalArticle = articleDao.getArticleById(articleId);
        Article originalBlog=null;

        return null;
    }

    /**
     * 取消点赞
     *
     * @param articleId
     * @param likeId
     * @return
     */
    @Override
    public void removeVote(int articleId, int likeId) {

    }

    @Override
    public Optional<List<Article>> getArticlesByCategoryId(int id) {
        return  Optional.ofNullable(articleDao.getArticlesByCategoryId(id));
    }


    @Override
    public List<Article> getArticlesByKeyword(String keyword) {
        return articleDao.getArticlesByKeyword(keyword);
    }

    @Override
    public Article getArticleById(Long id) {

        return articleDao.getArticleById(id);
    }

    @Override
    public List<Comment> getCommentById(Long article_id) {

        Article article = articleDao.getArticleById(article_id);

        if (null == article) {
            LOGGER.error("This article:{} is not exist", article);
            return null;
        }
        return null;
    }



    @HystrixCommand
    @CacheRemove(commandKey = "getArticleById")
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void updateArticle(@CacheKey(value = "id") final Article article) {
        articleDao.updateArticle(article);
    }

    @Override
    @Async("taskExecutor")
    public void executeAsyn() {
        LOGGER.info(" Start Execute Asyn task....");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info(" Aysn task has been done! ");
    }

    /**
     * Global Collapse.
     * @return {@link Future}
     * */
    @HystrixCollapser(batchMethod = "getArticleById",
            scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL,
            collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds", value = "1000")
    })
    @Override
    public Future<Article> collapsingGlobal(final Long id) {
        LOGGER.info("Single Request:[{}]", id);
        return null;
    }

    @Override
    public List<String> listAllAuthorName() {
        return articleDao
                .getAllAuthorName()
                .stream()
                .map(Article::getAuthor)
                .collect(Collectors.toList());
    }


    /**
     * @param articleParam List<Long>
     * @return return the List<Article>
     * */
    @HystrixCommand(commandKey = "getArticleById",
            fallbackMethod = "getArticleByIdError")
    public Optional<List<Article>> getArticleById(final List<Long> articleParam) {
        LOGGER.info("The current thread :[{}]", Thread.currentThread().getName());
        LOGGER.info("The request param:[{}]", articleParam);

        List<Article> articleList = new ArrayList<>();

        for (Long articleId : articleParam) {
            LOGGER.info("Request Id:[{}]", articleId);
            articleList.add(articleDao.getArticleById(articleId));
        }
        LOGGER.info("Result :[{}]", articleList);



        return Optional.ofNullable(articleList);

    }

    public List<Article> getArticleByIdError(final List<Long> articleParam) {

        List<Article> articleError =null;

        return articleError;
    }

    /**
     * @param id Long
     * */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteById( final Long id) {
        articleDao.deleteById(id);
    }

    /**
     * Delete Article By Author.
     * @param author String
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteArticleByAuthor(final String author) {
        articleDao.deleteArticleByAuthor(author);
    }

    /**
     * Delete Article.
     * @param article Article
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteArticle(final Article article) {
        articleDao.deleteArticle(article);
    }

    @Override
    public List<Article> getArticlesByAuthor(final String author) {
        return articleDao.getArticlesByAuthor(author);
    }

}
