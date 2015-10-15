package com.senatry.jmxInWeb.http;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 梁韦江
 * 2015年9月11日
 */
public abstract class BaseAction implements IRequestHandler {

	public abstract String getRequestUrl();

	protected Map<String, Object> newModel() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}

}
