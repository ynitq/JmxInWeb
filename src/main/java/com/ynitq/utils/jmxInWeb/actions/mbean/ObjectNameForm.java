package com.ynitq.utils.jmxInWeb.actions.mbean;

import com.ynitq.utils.jmxInWeb.exception.MyMissingParamException;
import com.ynitq.utils.jmxInWeb.utils.StringUtils;

/**
 * <pre>
 * MBean操作的form
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年10月15日
 */
public class ObjectNameForm {
	private String objectName;

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public void verifyObjectName() throws MyMissingParamException {
		if (StringUtils.isBlank(this.objectName)) {
			MyMissingParamException ex = new MyMissingParamException();
			ex.addMissingParam("objectName", "missing.objectName.desc");
			throw ex;
		}
	}
}
