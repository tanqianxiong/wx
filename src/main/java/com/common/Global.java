package com.common;

import org.springframework.beans.factory.annotation.Value;

public class Global {

	@Value("${config.wx.TOKEN}")
	public static String TOKEN="abcd1234";
	
	@Value("${config.wx.EncodingAESKey}")
	public static String EncodingAESKey="UDZkRMgIhftEB8zMI7epyJYR0bz3kOtG2O9IE1jcipj";

	@Value("${config.wx.AppID}")
	public static String AppID="wx13160138252e5ae8";

	@Value("${config.wx.AppSecret}")
	public static String AppSecret="e8b80580169baf028dc4271ef8efc5cb";
	
}
