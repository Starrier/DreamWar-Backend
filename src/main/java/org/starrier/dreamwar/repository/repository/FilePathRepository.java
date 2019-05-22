package org.starrier.dreamwar.repository.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.starrier.dreamwar.model.entity.Files;

/**
 * @author Starrier
 * @date 2018/12/21.
 */
@Repository
public interface FilePathRepository extends CrudRepository<Files,Long> {
}
