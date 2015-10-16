package com.senatry.jmxInWeb.json;

import com.senatry.jmxInWeb.config.AjaxReturnCodes;
import com.senatry.jmxInWeb.exception.BaseLogicException;
import com.senatry.jmxInWeb.mvc.BaseJsonResponse;

/**
 * <pre>
 * 基础的错误类型
 * </pre>
 * 
 * @author 梁韦江 2015年10月16日
 */
public class JsonErrorResponse extends BaseJsonResponse {
	private final String errorMsg;

	public JsonErrorResponse(BaseLogicException e) {
		this.setCode(AjaxReturnCodes.ERROR_UNKNOW);
		this.errorMsg = e.getErrorMsg();
	}

	public String getErrorMsg() {
		return errorMsg;
	}

}
