package org.starrier.dreamwar.file;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Starrier
 * @date 2018/12/21.
 */
@Repository
public interface FilePathRepository extends CrudRepository<Files,Long> {
}
