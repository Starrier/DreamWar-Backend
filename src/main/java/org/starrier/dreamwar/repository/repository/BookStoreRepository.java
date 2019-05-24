package org.starrier.dreamwar.repository.repository;


import org.starrier.dreamwar.model.vo.BookStoreWithBooks;
import org.starrier.dreamwar.model.entity.BookStore;

import java.util.List;

/**
 * @author Xiaoyue Xiao
 */
public interface BookStoreRepository {

    BookStore selectBookStoreById(Long id);

    List<BookStore> selectAllBookStores();

    BookStoreWithBooks selectBookStoreWithBooksById(Long id);

}
