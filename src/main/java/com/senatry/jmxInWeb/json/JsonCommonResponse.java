package com.senatry.jmxInWeb.json;

import com.senatry.jmxInWeb.mvc.BaseJsonResponse;

/**
 * <pre>
 * 通用的ajax返回类型
 * </pre>
 * 
 * @author 梁韦江 2015年10月16日
 */
public class JsonCommonResponse extends BaseJsonResponse {

	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
