package com.senatry.jmxInWeb.json;

import com.senatry.jmxInWeb.mvc.BaseJsonResponse;

/**
 * <pre>
 * Invoke opt response
 * </pre>
 * 
 * @author 梁韦江 2015年10月16日
 */
public class JsonInvokeOptResponse extends BaseJsonResponse {

	private boolean hasReturn;
	private String returnData = "NULL";

	private String opName;

	public boolean isHasReturn() {
		return hasReturn;
	}

	public void setHasReturn(boolean hasReturn) {
		this.hasReturn = hasReturn;
	}

	public String getReturnData() {
		return returnData;
	}

	public void setReturnData(Object returnData) {
		if (returnData != null) {
			this.returnData = returnData.toString();
		} else {
			this.returnData = "null";
		}
	}

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

}
