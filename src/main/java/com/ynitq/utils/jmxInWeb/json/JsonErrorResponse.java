package com.ynitq.utils.jmxInWeb.json;

import com.ynitq.utils.jmxInWeb.config.AjaxReturnCodes;
import com.ynitq.utils.jmxInWeb.mvc.BaseJsonResponse;

/**
 * <pre>
 * 基础的错误类型
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年10月16日
 */
public class JsonErrorResponse extends BaseJsonResponse {
	private final String errorMsg;

	public JsonErrorResponse(Throwable e) {
		this.setCode(AjaxReturnCodes.ERROR_UNKNOW);
		this.errorMsg = e.getMessage();
	}

	public String getErrorMsg() {
		return errorMsg;
	}

}
