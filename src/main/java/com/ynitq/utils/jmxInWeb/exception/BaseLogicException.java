package com.ynitq.utils.jmxInWeb.exception;

/**
 * <pre>
 * 用于包装action处理过程中的异常
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年10月15日
 */
public abstract class BaseLogicException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaseLogicException(String message) {
		super(message);
	}

	public BaseLogicException(Throwable cause) {
		super(cause);
	}

}
