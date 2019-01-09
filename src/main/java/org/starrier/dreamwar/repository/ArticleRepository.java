package org.starrier.dreamwar.repository;

import org.springframework.data.repository.CrudRepository;
import org.starrier.dreamwar.entity.Article;

public interface ArticleRepository extends CrudRepository<Article, Long> {

}
