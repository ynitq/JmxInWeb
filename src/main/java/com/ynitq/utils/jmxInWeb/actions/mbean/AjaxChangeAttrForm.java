package com.ynitq.utils.jmxInWeb.actions.mbean;

import com.ynitq.utils.jmxInWeb.exception.MyMalformedObjectNameException;
import com.ynitq.utils.jmxInWeb.exception.MyMissingParamException;
import com.ynitq.utils.jmxInWeb.utils.StringUtils;

/**
 * <pre>
 * MBean操作的form
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年10月15日
 */
public class AjaxChangeAttrForm extends ObjectNameForm {

	// name and value is use in change attribute value
	private String name;// attr name
	private String value;

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public void setName(String name) {
		this.name = name;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public void verifyForChangeAttrValue() throws MyMissingParamException, MyMalformedObjectNameException {
		this.verifyObjectName();

		if (StringUtils.isBlank(this.name)) {
			MyMissingParamException ex = new MyMissingParamException();
			ex.addMissingParam("objectName", "missing.name.desc");
			throw ex;
		}
	}

}
