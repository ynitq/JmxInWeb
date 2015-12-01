package com.ynitq.utils.jmxInWeb;

import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.BasicAuthenticator;
import com.ynitq.utils.jmxInWeb.utils.StringUtils;

/**
 * <pre>
 * 一个简单的用于认证类
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年10月19日
 */
public class SimpleHttpAuthenticator extends BasicAuthenticator {

	public SimpleHttpAuthenticator(String authRealm) {
		super(authRealm);
	}

	private final Map<String, String> authorizations = new HashMap<String, String>();

	@Override
	public boolean checkCredentials(String user, String pwd) {
		if (StringUtils.isNotBlank(user) && StringUtils.isNotBlank(pwd)) {
			return pwd.equals(authorizations.get(user));
		}
		return false;
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
