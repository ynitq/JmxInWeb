package com.senatry.jmxInWeb.actions.mbean;

import com.senatry.jmxInWeb.exception.MyMalformedObjectNameException;
import com.senatry.jmxInWeb.exception.MyMissingParamException;
import com.senatry.jmxInWeb.utils.StringUtils;

/**
 * <pre>
 * 执行mbean操作form
 * </pre>
 * 
 * @author 梁韦江 2015年10月15日
 */
public class AjaxInvokeOptForm extends ObjectNameForm {

	// name and value is use in change attribute value
	private String optName;// attr name
	private String[] paramValue;
	private String[] paramType;

	public void verify() throws MyMissingParamException, MyMalformedObjectNameException {
		this.verifyObjectName();

		if (StringUtils.isBlank(this.optName)) {
			MyMissingParamException ex = new MyMissingParamException();
			ex.addMissingParam("optName", "missing.optName.desc");
			throw ex;
		}
	}

	public String getOptName() {
		return optName;
	}

	public void setOptName(String optName) {
		this.optName = optName;
	}

	public String[] getParamValue() {
		return paramValue;
	}

	public void setParamValue(String[] paramValue) {
		this.paramValue = paramValue;
	}

	public String[] getParamType() {
		return paramType;
	}

	public void setParamType(String[] paramType) {
		this.paramType = paramType;
	}

}
