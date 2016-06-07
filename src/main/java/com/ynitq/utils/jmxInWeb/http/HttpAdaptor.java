package com.ynitq.utils.jmxInWeb.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.Executors;

import javax.management.MBeanServer;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import com.ynitq.utils.jmxInWeb.service.MBeanUtil;

/**
 * <pre>
 * HttpAdaptor sets the basic adaptor listening for HTTP requests
 * 
 * 用于管理 Http请求监听器的参数，例如端口、认证方式什么什么的
 * 
 * 本包中提供了最简单的认证方式 SimpleHttpAuthenticator。
 * 如果有心情，可以用其他的认证方式
 * 
 * </pre>
 * 
 * @see SimpleHttpAuthenticator 最简单的认证实现类
 * 
 * @author <a href="https://github.com/liangwj72">Alex (梁韦江)</a>
 * 2015年12月9日
 */
public class HttpAdaptor implements HttpAdaptorMBean {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HttpAdaptor.class);
	private static final String VERSION = "3.0.2";

	/**
	 * Port to listen for connections
	 */
	private int port = 8080;

	/**
	 * Host where to set the server socket
	 */
	private String host = "localhost";

	/**
	 * Indicates whether the server is running
	 */
	private boolean alive;

	private Date startDate;

	private long requestsCount;

	private final int acceptLimit = 10;

	private final String httpContextName = "/";

	private final int executerLimit = 10;

	private HttpServer httpserver;

	private final ActionDispatcher actionDispatcher = new ActionDispatcher();

	private Authenticator authenticator;

	public void start() throws IOException {
		if (this.isActive()) {
			log.warn("server is running");
		}
		httpserver = HttpServer.create(new InetSocketAddress(this.port), this.acceptLimit);
		httpserver.setExecutor(Executors.newFixedThreadPool(executerLimit));
		HttpContext hc = httpserver.createContext(this.httpContextName, actionDispatcher);
		hc.getFilters().add(new ParameterFilter());

		if (this.authenticator != null) {
			hc.setAuthenticator(authenticator);
		}

		httpserver.start();
		this.startDate = new Date();
		this.alive = true;

		log.info("MBean HttpAdaptor start: http://{}:{}{}", this.host, this.port, this.httpContextName);
	}

	/**
	 * Default Constructor added so that we can have some additional
	 * constructors as well.
	 */
	public HttpAdaptor(MBeanServer mBeanServer) {
		MBeanUtil.getInstance().setServer(mBeanServer);
	}

	/**
	 * Sets the value of the server's port
	 * 
	 * @param port
	 *            the new port's value
	 */
	public void setPort(int port) {
		if (alive) {
			throw new IllegalArgumentException("Not possible to change port with the server running");
		}
		this.port = port;
	}

	@Override
	public int getPort() {
		return port;
	}

	/**
	 * Sets the host name where the server will be listening
	 * 
	 * @param host
	 *            Server's host
	 */
	public void setHost(String host) {
		if (alive) {
			throw new IllegalArgumentException("Not possible to change port with the server running");
		}
		this.host = host;
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public boolean isActive() {
		return alive;
	}

	@Override
	public Date getStartDate() {
		return startDate;
	}

	@Override
	public long getRequestsCount() {
		return requestsCount;
	}

	@Override
	public String getVersion() {
		return VERSION;
	}

	/**
	 * Stops the HTTP daemon
	 */
	public void stop() {
		this.httpserver.stop(10);
	}

	public Authenticator getAuthenticator() {
		return authenticator;
	}

	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}
}
