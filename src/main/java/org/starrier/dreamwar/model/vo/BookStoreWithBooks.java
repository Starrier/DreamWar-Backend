package org.starrier.dreamwar.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.starrier.dreamwar.model.entity.BookStore;
import org.starrier.dreamwar.model.entity.Book;

import java.util.List;

/**
 * @author Starrier
 * @date 2019/3/10
 */
@Accessors(chain = true)
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class BookStoreWithBooks extends BookStore {

    private static final long serialVersionUID = -740463675258248874L;

    private List<Book> books;

}
