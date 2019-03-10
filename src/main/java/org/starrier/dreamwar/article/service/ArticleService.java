package org.starrier.dreamwar.article.service;


import org.starrier.dreamwar.article.entity.Article;
import org.starrier.dreamwar.comment.Comment;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

/**
 * @author Starrier
 * @date 2018/10/27.
 */
public interface ArticleService {

    /**
     * Insert Article with Id
     * @param article
     */
    void insertArticle(Article article);

    /**
     * Delete Article via id
     * @param id
     * */
    void deleteById(final Long id);

    /**
     *  Delete Article By Author
     * @param author
     * */
    void deleteArticleByAuthor(final String author);

    /**
    * Delete Article By Article
    * @param article
    */
    void deleteArticle(final Article article);

    /**
     * Fetch All Articles
     *
     * @return
     */
    List<Article> listArticle();

    /**
     * Fetch   ：   Fetch Comment By Article id
     * @param id
     * @return
     * */
    List<Comment> getCommentByArticle(Long id);

    /**
     * 点赞
     * @param articleId
     * @return
     */
    Article createVote(Long articleId);

    /**
     * 取消点赞
     * @param articleId
     * @param likeId
     * @return
     */
    void removeVote(int articleId, int likeId);

    Optional<List<Article>> getArticlesByCategoryId(int id);

    List<Article> getArticlesByKeyword(String keyword);

   Article getArticleById(Long id);

    List<Comment> getCommentById(Long article_id);

    List<Article> getArticlesByAuthor(final String author);

    /**
     * <p>Update Article.</p>
     * @param article
     * */
    void updateArticle(Article article);

    /**
     *  Asynchronous method.
     * */
    void executeAsynchronous();

    /**
     *
     * */
    Future<Article> collapsingGlobal(Long id);

    List<String> listAllAuthorName();
}
