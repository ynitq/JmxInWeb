package com.ynitq.utils.jmxInWeb.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.ynitq.utils.jmxInWeb.http.ParameterFilter;

/**
 * FORM绑定工具
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2012-3-8
 */
public class BinderUtil {

	private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(BinderUtil.class);

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
			updateObj(map, form);

			return form;
		} catch (Exception e) {
			LogUtil.traceError(log, e);
			return null;
		}
	}

	private static void updateObj(Map<String, List<String>> map, Object form) {

		for (Method m : form.getClass().getMethods()) {

			String methodName = m.getName();
			if (methodName.startsWith("set") && methodName.length() > 3) {

				// 必须是setXXX的方法
				Class<?>[] paramTypes = m.getParameterTypes();
				if (paramTypes.length == 1 && OpenTypeUtil.isOpenType(paramTypes[0])) {
					// 并且只有一个参数，并且是合法的类型
					String propName = StringUtils.lowerCaseFirstChar(methodName.substring(3));// 参数名
					Class<?> paramClass = paramTypes[0];

					List<String> values = map.get(propName);

					if (values != null && values.size() > 0) {

						if (log.isDebugEnabled()) {
							StringBuffer vb = new StringBuffer();
							for (String str : values) {
								vb.append(str).append(",");
							}
							log.debug(LogUtil.format("需要设置的属性:%s, 类型:%s, 值:%s", propName, paramClass.getSimpleName(),
									StringUtils.getStrSummary(vb.toString(), 20)));
						}

						Object propValue;

						if (paramClass.isArray()) {
							// 如果要设置的是数组
							propValue = Array.newInstance(paramClass.getComponentType(), values.size());
							for (int i = 0; i < values.size(); i++) {
								Object arg = OpenTypeUtil.parserFromString(values.get(i), paramClass.getComponentType());
								Array.set(propValue, i, arg);
							}
						} else {
							propValue = OpenTypeUtil.parserFromString(values.get(0), paramClass);
						}
						try {
							m.invoke(form, propValue);
						} catch (Exception e) {
							LogUtil.traceError(log, e);
						}
					}
				}
			}
		}
	}
}
