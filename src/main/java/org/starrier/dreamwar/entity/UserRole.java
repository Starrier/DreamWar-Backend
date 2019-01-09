/*
package org.starrier.dreamwar.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

*/
/**
 * @Author Starrier
 * @Time 2018/6/16.
 *//*

@Data
@Entity
@Table(name = "user_role")
public class UserRole {

    @Embeddable
    public static class Id implements Serializable {

        @Column(name = "user_id")
        private static final long serialVersionUID=1322120000551624359L;

        @Column(name = "role")
        @Enumerated(EnumType.STRING)
        protected Long userId;

        protected Role role;

        protected Id() {

        }

        protected Id(Long userId, Role role) {
            this.userId=userId;
            this.role=role;
        }

    }

    @EmbeddedId
    Id id = new Id();

    @Column(name = "ROLE",insertable = false,updatable = false)
    protected Role role;

}
*/
