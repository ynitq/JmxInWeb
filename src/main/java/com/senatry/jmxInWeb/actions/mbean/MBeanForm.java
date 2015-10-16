package com.senatry.jmxInWeb.actions.mbean;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import com.senatry.jmxInWeb.exception.MyMissingParamException;
import com.senatry.jmxInWeb.exception.MyMalformedObjectNameException;
import com.senatry.jmxInWeb.utils.StringUtils;

/**
 * <pre>
 * MBean操作的form
 * </pre>
 * 
 * @author 梁韦江 2015年10月15日
 */
public class MBeanForm {
	private String objectName;

	// name and value is use in change attribute value
	private String name;// attr name
	private String value;

	public String getName() {
		return name;
	}

	public String getObjectName() {
		return objectName;
	}

	public String getValue() {
		return value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void verifyOBjectName() throws MyMissingParamException {
		if (StringUtils.isBlank(this.objectName)) {
			MyMissingParamException ex = new MyMissingParamException();
			ex.addMissingParam("objectName", "missing.objectName.desc");
			throw ex;
		}
	}

	public void verifyForChangeAttrValue() throws MyMissingParamException, MyMalformedObjectNameException {
		this.verifyOBjectName();

		try {
			new ObjectName(this.objectName);
		} catch (MalformedObjectNameException e) {
			throw new MyMalformedObjectNameException(objectName);
		}

		if (StringUtils.isBlank(this.name)) {
			MyMissingParamException ex = new MyMissingParamException();
			ex.addMissingParam("objectName", "missing.name.desc");
			throw ex;
		}
	}

}
