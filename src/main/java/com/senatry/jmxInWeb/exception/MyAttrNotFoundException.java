package com.senatry.jmxInWeb.exception;

/**
 * <pre>
 * 要修改属性时，属性的值找不到
 * </pre>
 * 
 * @author 梁韦江 2015年10月16日
 */
public class MyAttrNotFoundException extends BaseLogicException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String attrName;

	public MyAttrNotFoundException(String attrName) {
		super();
		this.attrName = attrName;
	}

	@Override
	public String getMessage() {
		return String.format("找不到属性%s", this.attrName);
	}

}
