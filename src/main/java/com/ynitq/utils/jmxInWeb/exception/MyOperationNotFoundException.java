package com.ynitq.utils.jmxInWeb.exception;

/**
 * <pre>
 * 无法根据ObjectName找到对应的MBean
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年10月16日
 */
public class MyOperationNotFoundException extends BaseLogicException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String operationsInfo;

	public MyOperationNotFoundException(String operationsInfo) {
		super(operationsInfo);
		this.operationsInfo = operationsInfo;
	}

	@Override
	public String getMessage() {

		return String.format("Operation %s not found", this.operationsInfo);
	}

}
