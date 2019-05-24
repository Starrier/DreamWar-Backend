package org.starrier.dreamwar.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * AuthToke.
 *
 * @author Starrier
 * @date 2019/1/7
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthToken implements Serializable {

    private String token;
}
