package com.senatry.jmxInWeb.exception;

/**
 * <pre>
 *  ObjectName 格式错误
 * </pre>
 * 
 * @author 梁韦江 2015年10月16日
 */
public class MyMalformedObjectNameException extends BaseLogicException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String nameStr;

	public MyMalformedObjectNameException(String nameStr) {
		super();
		this.nameStr = nameStr;
	}

	@Override
	public String getErrorMsg() {
		return String.format("ObjectName格式错误: %s", this.nameStr);
	}

	public String getNameStr() {
		return nameStr;
	}

}
