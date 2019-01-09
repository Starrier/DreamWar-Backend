package org.starrier.dreamwar.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.starrier.dreamwar.entity.Article;
import org.starrier.dreamwar.entity.Comment;
import org.starrier.dreamwar.repository.ArticleDao;

import java.util.List;

/**
 * @author Starrier
 * @date 2019/1/9.
 */
@Mapper
public interface ArticleMapper extends ArticleDao {
    /**
     * Insert Article with Id.
     *
     * @param article Article
     */
    @Override
    void insertArticle(Article article);

    /**
     * Delete Article By id.
     *
     * @param id Long
     */
    @Override
    void deleteById(Long id);

    /**
     * Delete Article By Author.
     *
     * @param author String
     */
    @Override
    void deleteArticleByAuthor(String author);

    /**
     * Delete   :   Delete Article By article.
     *
     * @param article Article
     */
    @Override
    void deleteArticle(Article article);

    /**
     * 更新文章.
     *
     * @param article Article
     */
    @Override
    void updateArticle(Article article);

    /**
     * 通过时间显示文章列表.
     *
     * @return the result
     */
    @Override
    List<Article> listArticle();

    /**
     * 通过文章类别id查询文章列表.
     *
     * @param categoryId int
     * @return return the result
     */
    @Override
   List<Article> getArticlesByCategoryId(int categoryId);

    /**
     * 通过时间查询文章列表.
     *
     * @param date String
     * @return return the result
     */
    @Override
    List<Article> getArticlesByDate(String date);

    /**
     * 通过关键字搜索.
     *
     * @param keyword String
     * @return return the result
     */
    @Override
    List<Article> getArticlesByKeyword(String keyword);

    /**
     * 通过文章id显示文章详情.
     *
     * @param id Long
     * @return return the result
     */
    @Override
    Article getArticleById(Long id);

    /**
     * @param author via user
     * @return articles
     */
    @Override
    List<Article> getArticlesByAuthor(String author);

    /**
     * @param id Long
     * @return the result
     */
    @Override
   List<Comment> getCommentByArticle(Long id);

    @Override
    List<Article> getAllAuthorName();
}
