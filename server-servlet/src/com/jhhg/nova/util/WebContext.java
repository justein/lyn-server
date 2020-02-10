package com.jhhg.nova.util;

import com.jhhg.nova.entity.Entity;
import com.jhhg.nova.entity.Mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @ClassName: WebContext
 * @Description: TODO
 * @Author: Lyn
 * @Date: 2020/2/6 上午11:15
 * @version : V1.0
 */
public class WebContext {
    /**web.xml中，一个servlet可以对应多个url-pattern,
     * 但url-pattern不允许有重复，而且一个url-pattern只能对应到一个servlet
     * */
    /** key=servlet-name value = servlet-class */
    private Map<String, String> entityMap = new HashMap<>();
    /** key = url-pattern value = servlet-name*/
    private Map<String, String> mappingMap = new HashMap<>();

    public WebContext(List<Entity> entities, List<Mapping> mappings) {

        for (Entity entity:entities){
            entityMap.put(entity.getName(),entity.getClz());
        }

        for (Mapping mapping: mappings) {
            for (String pattern:mapping.getPatterns()){
                mappingMap.put(pattern,mapping.getName());
            }
        }

    }

    public String getEntityMap(String pattern) {
        String servletName = mappingMap.get(pattern);
        return entityMap.get(servletName);
    }
}
