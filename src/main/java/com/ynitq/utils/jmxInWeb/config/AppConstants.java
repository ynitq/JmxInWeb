package com.ynitq.utils.jmxInWeb.config;

/**
 * <pre>
 * 常量定义
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年9月11日
 */
public interface AppConstants {

	/**
	 * 静态文件的url前缀，如果url中以该路径开头，表示是静态文件
	 */
	public static final String STATICS_URL_PREFIX = "/statics/";

	/**
	 * 文件资源的包名
	 */
	public static final String RESOURCE_URL_ROOT = "com/ynitq/utils/jmxInWeb/files";

	/**
	 * 静态文件所在的资源路径
	 */
	public static final String RESOURCE_URL_OF_STATIC_RESOURCE = RESOURCE_URL_ROOT + "/statics/";

	/**
	 * 模板文件所在的资源路径
	 */
	public static final String RESOURCE_URL_OF_TEMPLATE = RESOURCE_URL_ROOT + "/";

}
