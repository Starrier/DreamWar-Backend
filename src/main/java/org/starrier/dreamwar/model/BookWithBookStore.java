package org.starrier.dreamwar.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.starrier.dreamwar.model.BookStore;
import org.starrier.dreamwar.model.entity.Book;


/**
 * @author Starrier
 * @Time 2018/6/8
 */
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class BookWithBookStore extends Book {

    private static final long serialVersionUID = -4858710159989616286L;

    private BookStore bookStore;

}
