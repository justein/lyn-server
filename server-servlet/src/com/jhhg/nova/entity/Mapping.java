package com.jhhg.nova.entity;

import java.util.HashSet;
import java.util.Set;

/***
 * @ClassName: Mapping,web.xml中的servlet-mapping
 * @Description: TODO
 * @Author: Lyn
 * @Date: 2020/2/6 上午7:36
 * @version : V1.0
 */
public class Mapping {

    private String name;
    private Set<String> patterns;

    public Mapping() {
        patterns = new HashSet<>();
    }

    public Mapping(Set<String> patterns) {
        this.patterns = patterns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getPatterns() {
        return patterns;
    }

    public void setPatterns(Set<String> patterns) {
        this.patterns = patterns;
    }
    /**往pattern容器中新增一个pattern*/
    public void addPattern(String pattern) {
        this.patterns.add(pattern);
    }

    @Override
    public String toString() {
        return "Mapping{" +
                "name='" + name + '\'' +
                ", patterns=" + patterns +
                '}';
    }
}
