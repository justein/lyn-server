package com.jhhg.nova.util;

import com.jhhg.nova.entity.Entity;
import com.jhhg.nova.entity.Mapping;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/***
 * @ClassName: WebXmlHandler
 * @Description: TODO
 * @Author: Lyn
 * @Date: 2020/2/6 上午7:44
 * @version : V1.0
 */
public class WebXmlHandler extends DefaultHandler {

    /**节点列表*/
    private List<Entity> entities = new ArrayList<>();
    private List<Mapping> mappings = new ArrayList<>();
    /**节点实体*/
    private Entity entity;

    private Mapping mapping;
    /**用于标记当前节点*/
    private boolean isMapping;
    /**用于标记当前节点属性*/
    private String currentAttrName;

    @Override
    public void startDocument() throws SAXException {
        System.out.println("解析文档开始");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        System.out.println(qName+"解析开始");
        /**记录当前正在解析的属性*/
        currentAttrName = qName;
        if ("servlet".equals(qName)) { //解析servlet
            isMapping = false;
            entity = new Entity();
        }else if ("servlet-mapping".equals(qName)){ //解析servlet-mapping
            isMapping = true;
            mapping = new Mapping();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String content = new String(ch, start, length).trim();

               if (content.length()>0) {
                   /**当前正在解析的是servlet*/
                   if (!isMapping) {
                       if ("servlet-name".equals(currentAttrName)) {
                           entity.setName(content);
                       }else if ("servlet-class".equals(currentAttrName)) {
                           entity.setClz(content);
                       }
                       /**当前正在解析的是servlet-mapping*/
                   }else  {
                       if ("servlet-name".equals(currentAttrName)) {
                           mapping.setName(content);
                       }else if ("url-pattern".equals(currentAttrName)) {
                           mapping.addPattern(content);
                       }
                   }
               }

            }



    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.out.println(qName+"解析结束");
        if ("servlet".equals(qName)) {
            entities.add(entity);
        }else if ("servlet-mapping".equals(qName)) {
            mappings.add(mapping);
            isMapping = false;
        }
        //处理解析节点时，后面内容为空的情况

        currentAttrName = null;
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("解析文档结束");
    }

    public List<Entity> getEntities(){
        return entities;
    }

    public List<Mapping> getMappings() {
        return mappings;
    }
}
