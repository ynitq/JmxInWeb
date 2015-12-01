package com.ynitq.utils.jmxInWeb.mvc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.ynitq.utils.jmxInWeb.exception.BaseLogicException;
import com.ynitq.utils.jmxInWeb.http.MyHttpRequest;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a>
 * 2015年9月11日
 */
public abstract class BaseAction {

	public abstract String getRequestUrl();

	protected Map<String, Object> newModel() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}

	public abstract void process(MyHttpRequest request) throws IOException, BaseLogicException;

}
