package com.ynitq.utils.jmxInWeb.exception;

/**
 * <pre>
 * 参数类型不可从String转换过来
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年10月15日
 */
public class MyInvalidParamTypeException extends BaseLogicException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String type;

	public MyInvalidParamTypeException(String type) {
		super(type);
		this.type = type;
	}

	@Override
	public String getMessage() {
		return String.format("参数类型错误: %s", type);
	}

}
