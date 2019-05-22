package org.starrier.dreamwar.controller.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.starrier.dreamwar.service.interfaces.BookService;
import org.starrier.dreamwar.model.entity.Book;
import org.starrier.dreamwar.utils.PageUtil;
import org.starrier.dreamwar.utils.PaginatedResult;
import org.starrier.dreamwar.utils.common.PageConstant;
import org.starrier.dreamwar.utils.common.ResourceNameConstant;
import org.starrier.dreamwar.utils.common.exception.ResourceNotFoundException;

import java.net.URI;

/**
 * @author Starrier
 * @date 2018/6/8
 */
@RestController
@RequestMapping("api/v1/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<?> getBooks(@RequestParam(value = "page", required = false) String pageString,
                                      @RequestParam(value = "perPage", required = false) String perPageString) {
        int page = PageUtil.parsePage(pageString, PageConstant.PAGE);
        int perPage = PageUtil.parsePerPage(perPageString, PageConstant.PER_PAGE);
        return ResponseEntity.ok(new PaginatedResult()
                .setData(bookService.getBooksByPage(page, perPage))
                .setCurrentPage(page)
                .setTotalPage(bookService.getTotalPage(perPage)));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable Long bookId) {
        return bookService.getBookById(bookId).map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException().setResourceName(ResourceNameConstant.BOOK).setId(bookId));
    }

    @PostMapping
    public ResponseEntity<?> postBook(@RequestBody Book book) {
        bookService.saveBook(book);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(book.getId()).toUri();
        return ResponseEntity.created(location).body(book);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<?> putBook(@PathVariable Long bookId, @RequestBody Book book) {
        assertBookExist(bookId);
        bookService.modifyBookOnNameById(book.setId(bookId));
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        assertBookExist(bookId);
        bookService.deleteBookById(bookId);
        return ResponseEntity.noContent().build();
    }

    private void assertBookExist(Long bookId) {
        bookService.getBookById(bookId).orElseThrow(() -> new ResourceNotFoundException()
                .setResourceName(ResourceNameConstant.BOOK)
                .setId(bookId));
    }

}
