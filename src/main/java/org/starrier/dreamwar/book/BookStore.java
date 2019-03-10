package org.starrier.dreamwar.book;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Starrier
 * @date 2018/6/8.
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "book_store")
public class BookStore implements Serializable {

    private static final long serialVersionUID = 1183385713216587274L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

}
