package org.starrier.dreamwar.user;

import java.io.Serializable;

/**
 * @author Starrier
 * @date 2018/12/31.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 695183437612916152L;
    private String username;
    private int age;

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public int getAge() {
        return age;
    }

    public User setAge(int age) {
        this.age = age;
        return this;
    }

}
