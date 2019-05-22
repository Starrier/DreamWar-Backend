package org.starrier.dreamwar.utils;

import com.alibaba.fastjson.JSONObject;
import org.starrier.dreamwar.model.vo.User;

/**
 * @author Starrier
 * @date 2019/1/10.
 */
public class JsonConvertUtils
{

    public static User convertJSONToObject(JSONObject json) {

        return JSONObject.toJavaObject(json, User.class);
    }
}
