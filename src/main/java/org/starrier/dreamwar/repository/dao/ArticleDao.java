package org.starrier.dreamwar.repository.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.starrier.dreamwar.model.entity.Article;
import org.starrier.dreamwar.model.entity.Comment;


import java.util.List;

/**
 * Article  CRUD.
 *
 * @author  Starrier
 * @date  2018/10/27.
 */
@Repository
public interface ArticleDao {

    /**
     * Insert Article with Id.
     * @param article Article
     */
    void insertArticle(Article article);

    /**
     * Delete Article By id.
     *
     * @param id Long
     */
    void deleteById(@Param("id") Long id);

    /**
     * Delete Article By Author.
     * @param author String
     */
    void deleteArticleByAuthor(String author);

    /**
     * Delete   :   Delete Article By article.
     * @param article Article
     * */
    void deleteArticle(Article article);

    /**
     * 更新文章.
     * @param article Article
     * */
    void updateArticle(Article article);

    /**
     * 通过时间显示文章列表.
     * @return the result
     */
    List<Article> listArticle();

    /**
     * 通过文章类别id查询文章列表.
     * @param categoryId int
     * @return return the result
     */
    @Select("select id from article where id = #{id}")
    List<Article> getArticlesByCategoryId(int categoryId);

    /**
     * 通过时间查询文章列表.
     * @param date String
     * @return return the result
     */
    List<Article> getArticlesByDate(String date);

    /**
     * 通过关键字搜索.
     * @param keyword String
     * @return return the result
     */
    List<Article> getArticlesByKeyword(String keyword);

    /**
     * 通过文章id显示文章详情.
     * @param id Long
     * @return return the result
     */
    Article getArticleById(Long id);

    /**
     * Fetch all the articles by {@code author}.
     *
     * @param author via user.
     * @return articles with list.
     * */
    List<Article> getArticlesByAuthor(String author);

    /**
     * fetch all the comments by article's id.
     *
     * @param id Long
     * @return the result
     * */
    List<Comment> getCommentByArticle(Long id);

    /**
     * Fetch all the author.
     *
     * @return the find result with list.
     * */
    List<Article> getAllAuthorName();

}
