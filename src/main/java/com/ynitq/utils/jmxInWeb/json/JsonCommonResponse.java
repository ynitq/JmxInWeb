package com.ynitq.utils.jmxInWeb.json;

import com.ynitq.utils.jmxInWeb.mvc.BaseJsonResponse;

/**
 * <pre>
 * 通用的ajax返回类型
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年10月16日
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
