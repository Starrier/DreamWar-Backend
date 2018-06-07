package org.starrier.dreamwar.config.druid;

/**
 * @Author Starrier
 * @Time 2018/6/5.
 */

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;
@WebServlet(urlPatterns = "/druid/*",
        initParams={
//            @WebInitParam(name="allow",value="192.168.16.110,127.0.0.1"),// IP白名单 (没有配置或者为空，则允许所有访问)
//            @WebInitParam(name="deny",value="192.168.16.111"),// IP黑名单 (存在共同时，deny优先于allow)
                @WebInitParam(name="loginUsername",value="admin"),// UserName
                @WebInitParam(name="loginPassword",value="admin"),// Password
                @WebInitParam(name="resetEnable",value="false")// 禁用HTML页面上的“Reset All”功能
        })
public class DruidServlet extends StatViewServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

}