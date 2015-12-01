package com.ynitq.utils.jmxInWeb.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;



/**
 * <pre>
 * 
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a>
 * 2015年9月10日
 */
public class ParameterFilter extends Filter {

	public static String ATTRIBUTE_NAME = "parameters";

	private static String CHARSET = "utf-8";

	@Override
	public String description() {
		return "Parses the requested URI for parameters";
	}

	public static List<String> getParameterNames(HttpExchange exchange) {
		Map<String, List<String>> parameters = getParameterMap(exchange);
		List<String> list = new LinkedList<String>();
		list.addAll(parameters.keySet());
		Collections.sort(list);
		return list;
	}

	public static String getParameter(HttpExchange exchange, String key) {
		if (key != null) {
			Map<String, List<String>> map = getParameterMap(exchange);
			List<String> list = map.get(key);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		}
		return null;
	}

	public static List<String> getParameters(HttpExchange exchange, String key) {
		if (key != null) {
			Map<String, List<String>> map = getParameterMap(exchange);
			return map.get(key);
		}
		return null;
	}

	public static Map<String, List<String>> getParameterMap(HttpExchange exchange) {
		@SuppressWarnings("unchecked")
		Map<String, List<String>> map = (Map<String, List<String>>) exchange.getAttribute(ATTRIBUTE_NAME);
		if (map == null) {
			return new HashMap<String, List<String>>();
		} else {
			return map;
		}
	}

	@Override
	public void doFilter(HttpExchange exchange, Chain chain)
			throws IOException {

		// 先初始化一个map
		Map<String, List<String>> parameters = new HashMap<String, List<String>>();
		parseGetParameters(exchange, parameters);
		parsePostParameters(exchange, parameters);
		// 结束后将map放到attribute中
		exchange.setAttribute(ATTRIBUTE_NAME, parameters);

		chain.doFilter(exchange);

	}

	/**
	 * 解析get方式的参数
	 * 
	 * @param exchange
	 * @param parameters2
	 * @throws UnsupportedEncodingException
	 */
	private void parseGetParameters(HttpExchange exchange, Map<String, List<String>> parameters)
			throws UnsupportedEncodingException {

		URI requestedUri = exchange.getRequestURI();
		String query = requestedUri.getRawQuery();
		parseQuery(query, parameters);
		exchange.setAttribute(ATTRIBUTE_NAME, parameters);
	}

	/**
	 * 解析post方式的参数
	 * 
	 * @param exchange
	 * @param parameters2
	 * @throws IOException
	 */
	private void parsePostParameters(HttpExchange exchange, Map<String, List<String>> parameters)
			throws IOException {

		if ("post".equalsIgnoreCase(exchange.getRequestMethod())) {
			InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), CHARSET);
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();
			parseQuery(query, parameters);
		}
	}

	private void parseQuery(String query, Map<String, List<String>> parameters)
			throws UnsupportedEncodingException {

		if (query != null) {
			String pairs[] = query.split("[&]");

			for (String pair : pairs) {
				String param[] = pair.split("[=]");

				String key = null;
				String value = null;
				if (param.length > 0) {
					key = URLDecoder.decode(param[0], CHARSET);
				}

				if (param.length > 1) {
					value = URLDecoder.decode(param[1], CHARSET);
				}

				List<String> values = parameters.get(key);
				if (values == null) {
					values = new ArrayList<String>();
					parameters.put(key, values);
				}
				values.add(value);
			}
		}
	}
}