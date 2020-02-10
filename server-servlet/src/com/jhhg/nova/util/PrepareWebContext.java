package com.jhhg.nova.util;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

/***
 * @ClassName: PrepareWebContext
 * @Description: TODO
 * @Author: Lyn
 * @Date: 2020/2/10 下午5:54
 * @version : V1.0
 */
public class PrepareWebContext {

    public static WebContext parseWebContext() {
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            WebXmlHandler webXmlHandler = new WebXmlHandler();
            saxParser.parse(Thread.currentThread().getContextClassLoader().
                    getResourceAsStream("com/jhhg/nova/web.xml"), webXmlHandler);
            WebContext webContext = new WebContext(webXmlHandler.getEntities(), webXmlHandler.getMappings());
            return webContext;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }
}
