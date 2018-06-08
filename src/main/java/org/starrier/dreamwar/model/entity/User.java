package org.starrier.dreamwar.model.entity;

/**
 * @Author Starrier
 * @Time 2018/6/5.
 */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GeneratorType;


import javax.persistence.*;
import java.io.Serializable;

@Accessors(chain = true)
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 7698862379923111158L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name="password")
    private String password;

}
