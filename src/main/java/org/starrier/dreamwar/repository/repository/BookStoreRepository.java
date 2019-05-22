package org.starrier.dreamwar.repository.repository;


import org.starrier.dreamwar.model.BookStoreWithBooks;
import org.starrier.dreamwar.model.BookStore;

import java.util.List;

/**
 * @author Xiaoyue Xiao
 */
public interface BookStoreRepository {

    BookStore selectBookStoreById(Long id);

    List<BookStore> selectAllBookStores();

    BookStoreWithBooks selectBookStoreWithBooksById(Long id);

}
