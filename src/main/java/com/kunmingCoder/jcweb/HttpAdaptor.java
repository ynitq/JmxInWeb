package com.kunmingCoder.jcweb;

/*
 *  Copyright (C) The MX4J Contributors.
 *  All rights reserved.
 *
 *  This software is distributed under the terms of the MX4J License version 1.0.
 *  See the terms of the MX4J License in the documentation provided with this software.
 */

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;

import javax.management.MBeanRegistration;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.commons.lang3.StringUtils;

import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * HttpAdaptor sets the basic adaptor listening for HTTP requests
 * 
 * @version $Revision: 1.14 $
 */
@SuppressWarnings("restriction")
public class HttpAdaptor implements MBeanRegistration, HttpHandler {

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
	 * Target server
	 */
	private MBeanServer curMBeanServer;

	/**
	 * Indicates whether the server is running
	 */
	private boolean alive;

	private String authenticationMethod = "none";

	private final Map<String, String> authorizations = new HashMap<String, String>();

	private Date startDate;

	private long requestsCount;

	private final int acceptLimit = 10;

	private final String httpContextName = "/mbean";

	private final int executerLimit = 10;

	private HttpServer httpserver;

	private final String authRealm = "MBeanConsole";

	public void start() throws IOException {
		if (this.isActive()) {
			log.warn("server is running");
		}
		httpserver = HttpServer.create(new InetSocketAddress(this.port), this.acceptLimit);
		httpserver.setExecutor(Executors.newFixedThreadPool(executerLimit));
		HttpContext hc = httpserver.createContext(this.httpContextName, this);
		hc.getFilters().add(new ParameterFilter());

		hc.setAuthenticator(new BasicAuthenticator(authRealm) {
			@Override
			public boolean checkCredentials(String user, String pwd) {
				if (StringUtils.isNotBlank(user) && StringUtils.isNotBlank(pwd)) {
					return pwd.equals(authorizations.get(user));
				}
				return false;
			}
		});

		httpserver.start();
		this.startDate = new Date();
		this.alive = true;

		log.info(String.format("MBean HttpAdaptor start: http://%s:%d%s", this.host, this.port, this.httpContextName));
	}

	/**
	 * Default Constructor added so that we can have some additional
	 * constructors as well.
	 */
	public HttpAdaptor() {
	}

	/**
	 * Overloaded constructor to allow the port to be set. The reason this was
	 * added was to allow the loading of this adaptor by the dynamic loading
	 * service of the MBean server and have the port set from a param in the
	 * mlet file. Example: (replaced lt & gt symbol with []) <br>
	 * [mlet code="mx4j.tools.adaptor.http.HttpAdaptor" <br>
	 * archive="mx4j.jar" <br>
	 * name="Server:name=HttpAdaptor"] <br>
	 * [arg type="int" value="12345"] <br>
	 * [/mlet]
	 * <p/>
	 * <p>
	 * This constructor uses the default host or the host must be set later.
	 * 
	 * @param port
	 *            The port on which the HttpAdaptor should listen
	 */
	public HttpAdaptor(int port) {
		this.port = port;
	}

	/**
	 * Overloaded constructor to allow the host to be set. The reason this was
	 * added was to allow the loading of this adaptor by the dynamic loading
	 * service of the MBean server and have the host set from a param in the
	 * mlet file. Example: (replaced lt & gt symbol with []) <br>
	 * [mlet code="mx4j.tools.adaptor.http.HttpAdaptor" <br>
	 * archive="mx4j.jar" <br>
	 * name="Server:name=HttpAdaptor"] <br>
	 * [arg type="java.lang.String" value="someserver.somehost.com"] <br>
	 * [/mlet]
	 * <p/>
	 * <p>
	 * This constructor uses the default port or the port must be set later.
	 * 
	 * @param host
	 *            The host on which the HttpAdaptor should listen
	 */
	public HttpAdaptor(String host) {
		this.host = host;
	}

	/**
	 * Overloaded constructor to allow the port to be set. The reason this was
	 * added was to allow the loading of this adaptor by the dynamic loading
	 * service of the MBean server and have the port set from a param in the
	 * mlet file. Example: (replaced lt & gt symbol with []) NOTE that the port
	 * must come before the host in the arg list of the mlet <br>
	 * [mlet code="mx4j.tools.adaptor.http.HttpAdaptor" <br>
	 * archive="mx4j.jar" <br>
	 * name="Server:name=HttpAdaptor"] <br>
	 * [arg type="int" value="12345"] <br>
	 * [arg type="java.lang.String" value="someserver.somehost.com"] <br>
	 * [/mlet]
	 * 
	 * @param port
	 *            The port on which the HttpAdaptor should listen
	 * @param host
	 *            The host on which the HttpAdaptor should listen
	 */
	public HttpAdaptor(int port, String host) {
		this.port = port;
		this.host = host;
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

	/**
	 * Returns the port where the server is running on. Default is 8080
	 * 
	 * @return HTTPServer's port
	 */
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

	/**
	 * Return the host name the server will be listening to. If null the server
	 * listen at the localhost
	 * 
	 * @return the current hostname
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Sets the Authentication Method.
	 * 
	 * @param method
	 *            none/basic/digest
	 */
	public void setAuthenticationMethod(String method) {
		if (alive) {
			throw new IllegalArgumentException("Not possible to change authentication method with the server running");
		}
		if (method == null || !(method.equals("none") || method.equals("basic") || method.equals("digest"))) {
			throw new IllegalArgumentException("Only accept methods none/basic/digest");
		}
		this.authenticationMethod = method;
	}

	/**
	 * Authentication Method
	 * 
	 * @return authentication method
	 */
	public String getAuthenticationMethod() {
		return authenticationMethod;
	}

	/**
	 * Indicates whether the server's running
	 * 
	 * @return The active value
	 */
	public boolean isActive() {
		return alive;
	}

	/**
	 * Starting date
	 * 
	 * @return The date when the server was started
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Requests count
	 * 
	 * @return The total of requests served so far
	 */
	public long getRequestsCount() {
		return requestsCount;
	}

	/**
	 * Gets the HttpAdaptor version
	 * 
	 * @return HttpAdaptor's version
	 */
	public String getVersion() {
		return VERSION;
	}

	/**
	 * Stops the HTTP daemon
	 */
	public void stop() {
		// TODO STOP
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

	/**
	 * Gathers some basic data
	 */
	@Override
	public ObjectName preRegister(MBeanServer server, ObjectName name) throws java.lang.Exception {
		this.curMBeanServer = server;
		return name;
	}

	@Override
	public void postRegister(Boolean registrationDone) {
	}

	@Override
	public void preDeregister() throws java.lang.Exception {
		// stop the server
		stop();
	}

	@Override
	public void postDeregister() {
	}

	public void setAuthMap(Map<String, String> map) {
		this.authorizations.putAll(map);
	}

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		Headers headers = httpExchange.getRequestHeaders();
		Set<Map.Entry<String, List<String>>> entries = headers.entrySet();

		StringBuffer response = new StringBuffer();
		for (Map.Entry<String, List<String>> entry : entries) {
			response.append(entry.toString() + "\n");
		}

		response.append("\n");

		if (log.isDebugEnabled()) {
			StringBuffer buff = new StringBuffer(200);
			buff.append("path=").append(httpExchange.getRequestURI().getPath()).append("\n");

			List<String> list = ParameterFilter.getParameterNames(httpExchange);
			for (String name : list) {
				String value = ParameterFilter.getParameter(httpExchange, name);
				buff.append(name);
				buff.append("=");
				buff.append(value);
				buff.append("\n");
			}

			log.debug(buff.toString());
		}

		byte[] bytes = response.toString().getBytes();
		httpExchange.sendResponseHeaders(200, bytes.length);
		OutputStream os = httpExchange.getResponseBody();
		os.write(bytes);
		os.flush();
		os.close();

		httpExchange.close();

	}

}
