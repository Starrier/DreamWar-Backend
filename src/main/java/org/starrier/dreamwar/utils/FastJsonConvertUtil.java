package org.starrier.dreamwar.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.starrier.dreamwar.user.entity.User;

/**
 * @author Starrier
 * @date 2019/1/10.
 */
public class FastJsonConvertUtil<T>
{

    /**
     * @author: Starrier
     * @createTime: 2018年9月12日 下午7:04:44
     * @history:
     * @param order
     * @return String
     */

    public static String convertObjectToJSON(User order)
    {
        return JSON.toJSONString(order);

    }

    /**
     * @author: 吴晓
     * @createTime: 2018年9月12日 下午7:15:33
     * @history:
     * @param message
     * @param class1
     * @return Order
     */

    public static User convertJSONToObject(String message, Class<User> class1)
    {
        JSONObject json = JSONObject.parseObject(message);
        return json.toJavaObject(class1);
    }

    public static JSONObject toJsonObject(Object javaBean)
    {
        return JSONObject.parseObject(JSONObject.toJSON(javaBean).toString());
    }

}
