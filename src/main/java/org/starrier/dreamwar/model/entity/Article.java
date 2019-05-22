package org.starrier.dreamwar.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Starrier
 * @date 2018/10/27.
 */
@Builder
@NoArgsConstructor
@Table(name = "article")
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Article implements Serializable {

    /**
     * Serialize Version
     */
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
    private Date createDate;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateDate;

    @Column
    private String summary;

    @Column
    private String category;


    @Column(name = "author")
    private String author;

    @Column
    private Long commentCount;

    @Version
    @Column
    private Long version;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "article_comment",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"))
    private List<Comment> articleComments;
}
