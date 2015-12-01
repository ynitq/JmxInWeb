package com.ynitq.utils.jmxInWeb.json;

import com.ynitq.utils.jmxInWeb.mvc.BaseJsonResponse;

/**
 * <pre>
 * bootstrap editable用的ajax返回类型
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年10月16日
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
