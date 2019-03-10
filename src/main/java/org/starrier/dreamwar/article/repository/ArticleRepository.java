package org.starrier.dreamwar.article.repository;

import org.springframework.data.repository.CrudRepository;
import org.starrier.dreamwar.article.entity.Article;

public interface ArticleRepository extends CrudRepository<Article, Long> {

}
