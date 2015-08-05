package com.common;

import org.springframework.beans.factory.annotation.Value;

public class Global {

	@Value("${config.wx.TOKEN}")
	public static String TOKEN;
	
	@Value("${config.wx.EncodingAESKey}")
	public static String EncodingAESKey;

	@Value("${config.wx.AppID}")
	public static String AppID;

	@Value("${config.wx.AppSecret}")
	public static String AppSecret;
	
}
