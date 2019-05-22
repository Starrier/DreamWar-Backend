package org.starrier.dreamwar.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The first refactor on 2019/1/7.
 * 1. refactor the class of role
 * 2. use annotation framework,lombok and spring jpa.
 *
 * @author  Starrier
 * @date  2018/6/16.
 */
@ApiModel(value = "UserBO's Role")
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@Data
@Entity
public class Role implements Serializable {

    /**
     * SerialVersion serial Version.
     * <p>This must be unique.</p>
     *
     * */
    private static final long serialVersionUID = -7453864066301624495L;

    /**
     *  The unique id,
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Role id")
    private Long id;

    /**
     * The role name.
     */
    @Column(nullable = false)
    @ApiModelProperty(value = "Role Authorization")
    private String roleName;


    /**
     * The detailed description of the user's role.
     */
    @Column
    @ApiModelProperty(value = "Description Role Authorization")
    private String description;

    /**
     * Set Protected.
     * Prevents direct calls to  nonparametric constructors.
     * */
    public Role(){}






}
