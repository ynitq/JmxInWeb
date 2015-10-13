package com.kunmingCoder.jmxInWeb.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kunmingCoder.jmxInWeb.AppConstants;
import com.kunmingCoder.jmxInWeb.actions.BaseAction;
import com.kunmingCoder.jmxInWeb.actions.StaticFileAction;
import com.kunmingCoder.jmxInWeb.actions.WelcomeAction;
import com.kunmingCoder.jmxInWeb.utils.LogUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import freemarker.template.TemplateException;

/**
 * <pre>
 * HttpServer的请求处理器
 * 
 * 判断是否是静态资源，如果是静态资源就从jar包中返回该资源
 * 
 * 否则就根据url寻找对应的处理器，如果找不到处理器就返回默认的欢迎页面
 * </pre>
 * 
 * @author 梁韦江 2015年9月11日
 */
public class HttpHandlerImpl implements HttpHandler {

	private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(HttpHandlerImpl.class);


	private final Map<String, BaseAction> handlerMap = new HashMap<String, BaseAction>();

	private final BaseAction staticFileAction = new StaticFileAction();

	public HttpHandlerImpl() {
		this.addHandler(new WelcomeAction());
	}

	private void addHandler(BaseAction handler) {
		this.handlerMap.put(handler.getRequestUrl(), handler);

	}

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		this.debugHttpRequest(httpExchange);

		String path = httpExchange.getRequestURI().getPath();

		try {
			if (path.startsWith(AppConstants.STATICS_PREFIX)) {
				// 先处理静态内容
				this.staticFileAction.process(httpExchange);
			} else {
				// 如果不是静态内容，就看该url是否有对应的处理器
				BaseAction action = this.handlerMap.get(path);
				if (action != null) {
					action.process(httpExchange);
				} else {
					// 404
					httpExchange.sendResponseHeaders(404, 0);
				}
			}
		} catch (TemplateException e) {
			LogUtil.traceError(log, e);
		}

		httpExchange.close();
	}

	private void debugHttpRequest(HttpExchange httpExchange) {
		if (log.isDebugEnabled()) {
			StringBuffer buff = new StringBuffer(200);
			buff.append("path=").append(httpExchange.getRequestURI().getPath());

			List<String> list = ParameterFilter.getParameterNames(httpExchange);
			for (String name : list) {
				String value = ParameterFilter.getParameter(httpExchange, name);
				buff.append("\n");
				buff.append(name);
				buff.append("=");
				buff.append(value);
			}

			log.debug(buff.toString());
		}

	}
}
