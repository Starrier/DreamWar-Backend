package org.starrier.dreamwar.web.controller;

/**
 * @Author Starrier
 * @Time 2018/6/5.
 */

import org.springframework.web.bind.annotation.*;

/**
 * @author 程序猿DD
 * @version 1.0.0
 * @date 16/5/19 下午1:27.
 * @blog http://blog.didispace.com
 */
@RestController
public class HelloController {

    @GetMapping(value = "/hello")
    @ResponseBody
    public String hello(@RequestParam String name) {
        return "Hello " + name;
    }

}

