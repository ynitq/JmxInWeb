package com.senatry.jmxInWeb.exception;

import javax.management.JMException;

/**
 * <pre>
 * 调用jmx的过程发送了错误
 * </pre>
 * 
 * @author 梁韦江 2015年10月15日
 */
public class MyJmException extends BaseLogicException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final JMException ex;

	public MyJmException(JMException ex) {
		super();
		this.ex = ex;
	}

	public JMException getEx() {
		return ex;
	}

	@Override
	public String getErrorMsg() {
		return this.ex.getMessage();
	}

}
