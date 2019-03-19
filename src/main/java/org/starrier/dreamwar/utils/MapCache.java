package org.starrier.dreamwar.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * @author Starrier
 * @date 2019/3/19.
 * <p>
 * Description :
 */
public class MapCache {

    private static final int DEFAULT_CACHES = 1024;

    private static final MapCache INS  = new MapCache();

    public static MapCache single(){
        return INS;
    }

    private Map<String,CacheObject> cachePool;


    public <T> T get(String key) {

    }

    @Data
    @AllArgsConstructor
    static class CacheObject{
        private String key;
        private Object  value;
        private long expired;
    }

}
