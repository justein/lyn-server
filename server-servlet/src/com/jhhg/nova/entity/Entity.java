package com.jhhg.nova.entity;

/***
 * @ClassName: Entity,web.xml中的servlet
 * @Description: TODO
 * @Author: Lyn
 * @Date: 2020/2/6 上午7:35
 * @version : V1.0
 */
public class Entity {

    private String name;
    private String clz;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClz() {
        return clz;
    }

    public void setClz(String clz) {
        this.clz = clz;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "name='" + name + '\'' +
                ", clz='" + clz + '\'' +
                '}';
    }
}
