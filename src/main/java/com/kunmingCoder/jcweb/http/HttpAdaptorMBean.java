package com.kunmingCoder.jcweb.http;

import java.util.Date;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 梁韦江
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
