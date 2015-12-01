package com.ynitq.utils.jmxInWeb.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.Executors;

import javax.management.MBeanServer;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import com.ynitq.utils.jmxInWeb.mvc.ActionManager;
import com.ynitq.utils.jmxInWeb.service.MBeanService;

/**
 * HttpAdaptor sets the basic adaptor listening for HTTP requests *
 */
public class HttpAdaptor implements HttpAdaptorMBean {

	private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(HttpAdaptor.class);
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

	private final ActionManager actionManager = new ActionManager();

	private Authenticator authenticator;

	public void start() throws IOException {
		if (this.isActive()) {
			log.warn("server is running");
		}
		httpserver = HttpServer.create(new InetSocketAddress(this.port), this.acceptLimit);
		httpserver.setExecutor(Executors.newFixedThreadPool(executerLimit));
		HttpContext hc = httpserver.createContext(this.httpContextName, actionManager);
		hc.getFilters().add(new ParameterFilter());

		if (this.authenticator != null) {
			hc.setAuthenticator(authenticator);
		}

		httpserver.start();
		this.startDate = new Date();
		this.alive = true;

		log.fatal(String.format("MBean HttpAdaptor start: http://%s:%d%s", this.host, this.port, this.httpContextName));
	}

	/**
	 * Default Constructor added so that we can have some additional
	 * constructors as well.
	 */
	public HttpAdaptor(MBeanServer mBeanServer) {
		MBeanService.getInstance().setServer(mBeanServer);
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
