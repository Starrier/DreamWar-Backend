package org.starrier.dreamwar.controller;


import org.starrier.dreamwar.common.PageConstant;
import org.starrier.dreamwar.common.ResourceNameConstant;
import org.starrier.dreamwar.service.BookService;
import org.starrier.dreamwar.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.starrier.dreamwar.model.dto.PaginatedResult;
import org.starrier.dreamwar.model.entity.Book;
import org.starrier.dreamwar.exception.ResourceNotFoundException;

import java.net.URI;

/**
 * @author Starrier
 * @Time 2018/6/8
 */
@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<?> getBooks(@RequestParam(value = "page", required = false) String pageString,
                                      @RequestParam(value = "per_page", required = false) String perPageString) {
        // Parse request parameters
        int page = PageUtil.parsePage(pageString, PageConstant.PAGE);
        int perPage = PageUtil.parsePerPage(perPageString, PageConstant.PER_PAGE);

        return ResponseEntity
                .ok(new PaginatedResult()
                        .setData(bookService.getBooksByPage(page, perPage))
                        .setCurrentPage(page)
                        .setTotalPage(bookService.getTotalPage(perPage)));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable Long bookId) {
        return bookService
                .getBookById(bookId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException()
                        .setResourceName(ResourceNameConstant.BOOK)
                        .setId(bookId));
    }

    @PostMapping
    public ResponseEntity<?> postBook(@RequestBody Book book) {
        bookService.saveBook(book);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(book.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(book);

    }

    @PutMapping("/{bookId}")
    public ResponseEntity<?> putBook(@PathVariable Long bookId, @RequestBody Book book) {
        assertBookExist(bookId);

        bookService.modifyBookOnNameById(book.setId(bookId));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(book);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        assertBookExist(bookId);

        bookService.deleteBookById(bookId);

        return ResponseEntity
                .noContent()
                .build();
    }

    /********************************** HELPER METHOD **********************************/
    private void assertBookExist(Long bookId) {
        bookService
                .getBookById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException()
                        .setResourceName(ResourceNameConstant.BOOK)
                        .setId(bookId));
    }

}