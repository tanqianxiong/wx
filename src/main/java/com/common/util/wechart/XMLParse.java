/**
 * 对公众平台发送给公众账号的消息加解密示例代码.
 * 
 * @copyright Copyright (c) 1998-2014 Tencent Inc.
 */

// ------------------------------------------------------------------------

package com.common.util.wechart;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import org.dom4j.Document;
import org.dom4j.Element;

/**
 * XMLParse class
 *
 * 提供提取消息格式中的密文及生成回复消息格式的接口.
 */
public class XMLParse {

	/**
	 * 提取出xml数据包中的加密消息
	 * @param xmltext 待提取的xml字符串
	 * @return 提取出的加密消息字符串
	 * @throws AesException 
	 */
	public static Object[] extract(String xmltext) throws AesException     {
		Object[] result = new Object[3];
		try {
//			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//			DocumentBuilder db = dbf.newDocumentBuilder();
//			StringReader sr = new StringReader(xmltext);
//			InputSource is = new InputSource(sr);
//			Document document = db.parse(is);
//
//			Element root = document.getDocumentElement();
//			NodeList nodelist1 = root.getElementsByTagName("Encrypt");
//			NodeList nodelist2 = root.getElementsByTagName("ToUserName");
//			result[0] = 0;
//			result[1] = nodelist1.item(0).getNodeValue();//.getTextContent();
//			result[2] = nodelist2.item(0).getNodeValue();//.getTextContent();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.ParseXmlError);
		}
	}
	/**
	 * 提取出xml数据包中的加密消息
	 * @param request 可以提取出待提取的xml字符串
	 * @return Map<String,String> 所有元素的键值对 
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> extract(HttpServletRequest request) {
        /*// 从request中取得输入流  
        ServletInputStream inputStream;
        // 将解析结果存储在HashMap中  
        Map<String, String> map = new HashMap<String, String>();  
		try {
			inputStream = request.getInputStream();
			// 读取输入流  
	        SAXReader reader = new SAXReader();
	        Document document = reader.read(inputStream); 
	        // 得到xml根元素  
	        Element root = document.getRootElement();  
	        // 得到根元素的全部子节点  
	        List<Element> elementList = root.elements();
	        // 遍历全部子节点  
	        for (Element e : elementList)  {
	            map.put(e.getName(), e.getTextTrim()); 
	        }
	        // 释放资源  
	        inputStream.close();  
	        inputStream = null; 
		} catch (IOException | DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/         

        // 将解析结果存储在HashMap中  
        Map<String, String> map = new HashMap<String, String>(); 
		//处理接收消息    
        ServletInputStream in;
		try {
			in = request.getInputStream();
			//将POST流转换为XStream对象  
	        XStream xs = new XStream(new DomDriver());  
	        //将指定节点下的xml节点数据映射为对象  
	        xs.alias("xml", InputMessage.class);  
	        //将流转换为字符串  
	        StringBuilder xmlMsg = new StringBuilder();  
	        byte[] b = new byte[4096];  
	        for (int n; (n = in.read(b)) != -1;) {  
	            xmlMsg.append(new String(b, 0, n, "UTF-8"));  
	        }  
	        //将xml内容转换为InputMessage对象  
	        InputMessage inputMsg = (InputMessage) xs.fromXML(xmlMsg.toString()); 
	        map.put("MsgType", inputMsg.getMsgType());
	        map.put("Event", inputMsg.getEvent());
	        map.put("EventKey", inputMsg.getEventKey());
	        map.put("FromUserName", inputMsg.getFromUserName());
	        map.put("ToUserName", inputMsg.getToUserName());
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        
        return map;  
	}
	/**
	 * 生成xml消息
	 * @param encrypt 加密后的消息密文
	 * @param signature 安全签名
	 * @param timestamp 时间戳
	 * @param nonce 随机字符串
	 * @return 生成的xml字符串
	 */
	public static String generate(String encrypt, String signature, String timestamp, String nonce) {

		String format = "<xml>\n" + "<Encrypt><![CDATA[%1$s]]></Encrypt>\n"
				+ "<MsgSignature><![CDATA[%2$s]]></MsgSignature>\n"
				+ "<TimeStamp>%3$s</TimeStamp>\n" + "<Nonce><![CDATA[%4$s]]></Nonce>\n" + "</xml>";
		return String.format(format, encrypt, signature, timestamp, nonce);

	}
}
