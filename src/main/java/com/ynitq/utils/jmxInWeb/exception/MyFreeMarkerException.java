package com.ynitq.utils.jmxInWeb.exception;

import freemarker.template.TemplateException;

/**
 * <pre>
 * 调用freemarker的过程发送了错误
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年10月15日
 */
public class MyFreeMarkerException extends BaseLogicException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final TemplateException ex;

	public MyFreeMarkerException(TemplateException ex) {
		super(ex);
		this.ex = ex;
	}

	public TemplateException getEx() {
		return ex;
	}

	@Override
	public String getMessage() {
		return this.ex.getMessage();
	}

}
