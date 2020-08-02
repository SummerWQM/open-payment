package com.util;

import java.security.SecureRandom;
import java.util.Random;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.*;

import org.w3c.dom.Element;
import org.w3c.dom.Document;


public class Helper {

    private static final String strMap = "abcdefghijklmnopqrstuvwxyz0123456789";

    private static final Random random = new SecureRandom();

    public static String getRandomString(int len) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i++) {
            sb.append(strMap.charAt(random.nextInt(strMap.length())));
        }

        return sb.toString();
    }

    public static String mapToXml(Map<String, String> data) throws Exception {
        Document document = WXPayXmlUtil.newDocument();
        Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key : data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);

        transformer.transform(source, result);
        String output = writer.getBuffer().toString();
        try {
            writer.close();

        } catch (Exception ex) {
        }
        return output;
    }

}
