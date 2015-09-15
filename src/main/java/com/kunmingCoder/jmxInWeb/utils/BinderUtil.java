package com.kunmingCoder.jmxInWeb.utils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.kunmingCoder.jmxInWeb.http.ParameterFilter;
import com.sun.net.httpserver.HttpExchange;

/**
 * FORM绑定工具
 * 
 * @author 梁韦江 2012-3-8
 */
public class BinderUtil {

	private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(BinderUtil.class);

	public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 从request中获得form对象
	 * 
	 * @param exchange
	 * @param formClazz
	 *            form的类
	 * @return
	 */
	public static <T> T bindForm(HttpExchange exchange, Class<T> formClazz) {
		try {
			T form = formClazz.newInstance();
			Map<String, List<String>> map = ParameterFilter.getParameterMap(exchange);
			BinderEditorSupport.updateObj(map, form);

			return form;
		} catch (Exception e) {
			LogUtil.traceError(log, e);
			return null;
		}
	}
}
