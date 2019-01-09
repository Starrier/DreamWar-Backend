package org.starrier.dreamwar.service;


import org.starrier.dreamwar.entity.Article;
import org.starrier.dreamwar.entity.Comment;

import java.util.List;
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
    void deleteById(Long id);

    /**
     *  Delete Article By Author
     * @param author
     * */
    void deleteArticleByAuthor(String author);

    /**
    * Delete Article By Article
    * @param article
    */
    void deleteArticle(Article article);

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

    List<Article> getArticlesByCategoryId(int id);

    List<Article> getArticlesByKeyword(String keyword);

    Article getArticleById(Long id);

    List<Comment> getCommentById(Long article_id);

    List<Article> getArticlesByAuthor(final String author);

    void updateArticle(Article article);

    void executeAsyn();

    Future<Article> collapsingGlobal(Long id);

    List<String> listAllAuthorName();
}
