package org.starrier.dreamwar.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


/**
 * @author Starrier
 * @Time 2018/6/8
 */
@Accessors(chain = true)
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class BookWithBookStore extends Book {

    private static final long serialVersionUID = -4858710159989616286L;

    private BookStore bookStore;

}
