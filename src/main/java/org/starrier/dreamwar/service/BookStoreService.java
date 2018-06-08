package org.starrier.dreamwar.service;


import org.starrier.dreamwar.model.entity.BookStore;
import org.starrier.dreamwar.model.entity.BookStoreWithBooks;

import java.util.List;
import java.util.Optional;

/**
 * @author Starrier
 * @Time 2018/6/8
 */
public interface BookStoreService {

    Optional<BookStore> getBookStoreById(Long id);

    List<String> getAllBookStoreNames();

    Optional<BookStoreWithBooks> getBookStoreWithBooksById(Long id);

}
