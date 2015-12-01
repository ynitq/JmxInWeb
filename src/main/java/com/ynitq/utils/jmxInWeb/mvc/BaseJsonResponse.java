package com.ynitq.utils.jmxInWeb.mvc;

import com.ynitq.utils.jmxInWeb.config.AjaxReturnCodes;

/**
 * <pre>
 * Ajax调用返回的json对象的基类
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年10月16日
 */
public class BaseJsonResponse {

	private int code = AjaxReturnCodes.OK;


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean isSuccess() {
		return this.code == AjaxReturnCodes.OK;
	}

}
