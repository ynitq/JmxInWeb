package com.fireNut.jmxInWeb.http;

/*
 *  Copyright (C) The MX4J Contributors.
 *  All rights reserved.
 *
 *  This software is distributed under the terms of the MX4J License version 1.0.
 *  See the terms of the MX4J License in the documentation provided with this software.
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import javax.management.MBeanServer;

import com.fireNut.jmxInWeb.service.MBeanService;
import com.fireNut.jmxInWeb.utils.StringUtils;
import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

/**
 * HttpAdaptor sets the basic adaptor listening for HTTP requests
 * 
 * @version $Revision: 1.14 $
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

	private final Map<String, String> authorizations = new HashMap<String, String>();

	private Date startDate;

	private long requestsCount;

	private final int acceptLimit = 10;

	private final String httpContextName = "/";

	private final int executerLimit = 10;

	private HttpServer httpserver;

	private final String authRealm = "MBeanConsole";

	private final ActionManager actionManager = new ActionManager();

	public void start() throws IOException {
		if (this.isActive()) {
			log.warn("server is running");
		}
		httpserver = HttpServer.create(new InetSocketAddress(this.port), this.acceptLimit);
		httpserver.setExecutor(Executors.newFixedThreadPool(executerLimit));
		HttpContext hc = httpserver.createContext(this.httpContextName, actionManager);
		hc.getFilters().add(new ParameterFilter());

		if (!this.authorizations.isEmpty()) {
			hc.setAuthenticator(new BasicAuthenticator(authRealm) {
				@Override
				public boolean checkCredentials(String user, String pwd) {
					if (StringUtils.isNotBlank(user) && StringUtils.isNotBlank(pwd)) {
						return pwd.equals(authorizations.get(user));
					}
					return false;
				}
			});
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kunmingCoder.jcweb.HttpAdaptorMBean1#getPort()
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kunmingCoder.jcweb.HttpAdaptorMBean1#getHost()
	 */
	@Override
	public String getHost() {
		return host;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kunmingCoder.jcweb.HttpAdaptorMBean1#isActive()
	 */
	@Override
	public boolean isActive() {
		return alive;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kunmingCoder.jcweb.HttpAdaptorMBean1#getStartDate()
	 */
	@Override
	public Date getStartDate() {
		return startDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kunmingCoder.jcweb.HttpAdaptorMBean1#getRequestsCount()
	 */
	@Override
	public long getRequestsCount() {
		return requestsCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kunmingCoder.jcweb.HttpAdaptorMBean1#getVersion()
	 */
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

	/**
	 * Adds an authorization pair as username/password
	 */
	public void addAuthorization(String username, String password) {
		if (username == null || password == null) {
			throw new IllegalArgumentException("username and passwords cannot be null");
		}
		authorizations.put(username, password);
	}

	public void setAuthMap(Map<String, String> map) {
		if (map != null) {
			this.authorizations.putAll(map);
		}
	}
}
