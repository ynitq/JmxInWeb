package com.ynitq.utils.jmxInWeb.http;

import java.util.Date;

/**
 * <pre>
 * 按照MBean的规范写的东西
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a>
 * 2015年9月11日
 */
public interface HttpAdaptorMBean {

	/**
	 * Returns the port where the server is running on. Default is 8080
	 * 
	 * @return HTTPServer's port
	 */
	public int getPort();

	/**
	 * Return the host name the server will be listening to. If null the server
	 * listen at the localhost
	 * 
	 * @return the current hostname
	 */
	public String getHost();

	/**
	 * Indicates whether the server's running
	 * 
	 * @return The active value
	 */
	public boolean isActive();

	/**
	 * Starting date
	 * 
	 * @return The date when the server was started
	 */
	public Date getStartDate();

	/**
	 * Requests count
	 * 
	 * @return The total of requests served so far
	 */
	public long getRequestsCount();

	/**
	 * Gets the HttpAdaptor version
	 * 
	 * @return HttpAdaptor's version
	 */
	public String getVersion();

}
