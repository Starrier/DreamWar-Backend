package org.starrier.dreamwar.repository.repository;

import org.springframework.data.repository.CrudRepository;
import org.starrier.dreamwar.model.entity.Article;

public interface ArticleRepository extends CrudRepository<Article, Long> {

}
