package org.starrier.dreamwar.book;


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
