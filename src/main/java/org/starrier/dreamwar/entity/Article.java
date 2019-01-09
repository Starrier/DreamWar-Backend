package org.starrier.dreamwar.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author  Starrier
 * @date  2018/10/27.
 */
@Accessors(chain = true)
@Builder
@Data
@Table(name = "article")
@Entity
@AllArgsConstructor
public class Article implements Serializable {

    /**
     *  Serialize Version
     * */
    private static final long serialVersionUID = -8372839858844609232L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date create_date;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date update_date;

    @Column
    private String summary;

    @Column
    private String category;


    @Column(name = "author")
    private String author;

    @Column
    private Long comment_count;

    @Version
    @Column
    private Long version;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "article_comment",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"))
    private List<Comment> article_comments;

    public Article(){}
}
