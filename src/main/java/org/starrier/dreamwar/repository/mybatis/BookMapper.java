package org.starrier.dreamwar.repository.mybatis;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.starrier.dreamwar.model.entity.Book;
import org.starrier.dreamwar.repository.BookRepository;

import java.util.List;

/**
 * @author Xiaoyue Xiao
 */
@Mapper
public interface BookMapper extends BookRepository {

    @Override
    List<Book> selectBooksByLowPriceAndHighPrice(@Param("lowPrice") Double lowPrice, @Param("highPrice") Double highPrice);

    @Override
    List<Book> selectBooksByPage(@Param("offset") Integer offset, @Param("perPage") Integer perPage);

}
