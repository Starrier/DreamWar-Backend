package org.starrier.dreamwar.repository.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.starrier.dreamwar.entity.Book;
import org.starrier.dreamwar.repository.BookDao;

import java.util.List;

/**
 * @author  Starrier
 */
@Mapper
public interface BookMapper extends BookDao {

    @Override
    List<Book> selectBooksByLowPriceAndHighPrice(@Param("lowPrice") Double lowPrice, @Param("highPrice") Double highPrice);

    @Override
    List<Book> selectBooksByPage(@Param("offset") Integer offset, @Param("perPage") Integer perPage);

}
