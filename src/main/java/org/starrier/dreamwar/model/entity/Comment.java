package org.starrier.dreamwar.model.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Starrier
 * @date 2018/10/27.
 */
@Data
@Entity
public class Comment implements Serializable {


    /**
     *  Serialize Version
     * */
    private static final long serialVersionUID = -1957151737557415321L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long article_id;

    @Column
    private String comment;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8",locale = "zh")
    @Column
    private Date date;
}


