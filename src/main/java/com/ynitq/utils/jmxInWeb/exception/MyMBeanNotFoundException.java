package com.ynitq.utils.jmxInWeb.exception;

/**
 * <pre>
 * 无法根据ObjectName找到对应的MBean
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年10月16日
 */
public class MyMBeanNotFoundException extends BaseLogicException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String name;

	public MyMBeanNotFoundException(String name) {
		super(name);
		this.name = name;
	}

	@Override
	public String getMessage() {
		return String.format("找不到MBean:%s", this.name);
	}

}
