package org.starrier.dreamwar.file;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Starrier
 * @date 2018/12/21.
 */
@Entity
@Data
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String path;
}
