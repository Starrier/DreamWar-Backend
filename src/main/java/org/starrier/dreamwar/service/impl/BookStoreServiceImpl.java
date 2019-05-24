package org.starrier.dreamwar.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.starrier.dreamwar.model.entity.BookStore;
import org.starrier.dreamwar.repository.repository.BookStoreRepository;
import org.starrier.dreamwar.model.vo.BookStoreWithBooks;
import org.starrier.dreamwar.service.interfaces.BookStoreService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Starrier
 * @Time 2018/6/8
 */
@Service(value = "bookStoreService")
public class BookStoreServiceImpl implements BookStoreService {

    private final BookStoreRepository bookStoreRepository;

    @Autowired
    public BookStoreServiceImpl(BookStoreRepository bookStoreRepository) {
        this.bookStoreRepository = bookStoreRepository;
    }

    @Override
    public Optional<BookStore> getBookStoreById(Long id) {
        return Optional.ofNullable(bookStoreRepository.selectBookStoreById(id));
    }

    @Override
    public List<String> getAllBookStoreNames() {
        return bookStoreRepository
                .selectAllBookStores()
                .stream()
                .map(BookStore::getName)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BookStoreWithBooks> getBookStoreWithBooksById(Long id) {
        return Optional.ofNullable(bookStoreRepository.selectBookStoreWithBooksById(id));
    }
}
