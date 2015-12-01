package com.ynitq.utils.jmxInWeb.exception;

/**
 * <pre>
 * 要修改属性时，属性的值找不到
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年10月16日
 */
public class MyAttrNotFoundException extends BaseLogicException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String attrName;

	public MyAttrNotFoundException(String attrName) {
		super(attrName);
		this.attrName = attrName;
	}

	@Override
	public String getMessage() {
		return String.format("找不到属性%s", this.attrName);
	}

}
