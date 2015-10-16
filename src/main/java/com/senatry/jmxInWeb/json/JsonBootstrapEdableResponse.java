package com.senatry.jmxInWeb.json;

import com.senatry.jmxInWeb.mvc.BaseJsonResponse;

/**
 * <pre>
 * bootstrap editable用的ajax返回类型
 * </pre>
 * 
 * @author 梁韦江 2015年10月16日
 */
public class JsonBootstrapEdableResponse extends BaseJsonResponse {

	private final String newValue;

	public JsonBootstrapEdableResponse(String newValue) {
		super();
		this.newValue = newValue;
	}

	public String getNewValue() {
		return newValue;
	}
}
