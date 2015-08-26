package com.common.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

public class JsonUtil {

	/**
	 * 返回时的数据编码
	 */
	private static String CONTENT_TYPE = "text/xml;charset=UTF-8";

	/**
	 * 根据实体对象生成JSON
	 * 
	 * @param response
	 * @param entity
	 *            实体对象
	 * @throws IOException
	 */
	public static void writeCommonJson(HttpServletResponse response,
			Object object) throws IOException {
		writeCommonJson(response, object, JsonUtil.getConfig());
	}

	/**
	 * 根据实体对象生成JSON
	 * 
	 * @param response
	 * @param entity
	 *            实体对象
	 * @throws IOException
	 * @param config
	 *            json生成时的配置对象
	 */
	public static void writeCommonJson(HttpServletResponse response,
			Object object, JsonConfig config) throws IOException {
		JSONObject jsonObject = JSONObject.fromObject(object, config);
		response.setContentType(CONTENT_TYPE);
		response.getWriter().write(jsonObject.toString());
	}


	/**
	 * 根据实体集合生成JSON
	 * 
	 * @param response
	 * @param list
	 *            实体集合
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static void writeCommonJson(HttpServletResponse response, List list)
			throws IOException {
		writeCommonJson(response, list, new JsonConfig());
	}

	/**
	 * 根据实体集合生成JSON
	 * 
	 * @param response
	 * @param list
	 *            实体集合
	 * @throws IOException
	 * @param config
	 *            json生成时的配置对象
	 */
	@SuppressWarnings("unchecked")
	public static void writeCommonJson(HttpServletResponse response, List list,
			JsonConfig config) throws IOException {
		JSONArray jsonArray = JSONArray.fromObject(list, config);
		response.setContentType(CONTENT_TYPE);
		response.getWriter().write(jsonArray.toString());
	}

	public static JsonConfig getConfig() {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "" });
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		return jsonConfig;
	}
	
	/**
	 * 根据实体对象生成JSON字符串
	 * 
	 * @param response
	 * @param object
	 *            对象
	 */
	public static String object2JsonStr(Object object) {
		JSONObject jsonObject = JSONObject.fromObject(object);
		return jsonObject.toString();
	}
	/**
	 * 设置返回的头部编码
	 * 
	 * @param response
	 * @return void           
	 */
	public static void setContentType(HttpServletResponse response) {
		response.setContentType(CONTENT_TYPE);
	}
}
