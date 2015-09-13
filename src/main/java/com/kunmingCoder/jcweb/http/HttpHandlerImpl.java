package com.kunmingCoder.jcweb.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kunmingCoder.jcweb.AppConstants;
import com.kunmingCoder.jcweb.actions.BaseAction;
import com.kunmingCoder.jcweb.actions.WelcomeAction;
import com.kunmingCoder.jcweb.utils.LogUtil;
import com.kunmingCoder.jcweb.utils.StringUtils;
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

	private static String STATICS_PREFIX = "/statics/";

	private final Map<String, BaseAction> handlerMap = new HashMap<String, BaseAction>();

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

		if (path.startsWith(STATICS_PREFIX)) {
			// 先处理静态内容
			String fileName = path.substring(STATICS_PREFIX.length());
			byte[] sendBytes = StringUtils.loadFileFromClassPath(AppConstants.STATIC_RESOURCE_PREFIX + fileName);
			if (sendBytes == null) {
				// 404
				httpExchange.sendResponseHeaders(404, 0);
			} else {
				this.sendResponse(httpExchange, sendBytes);
			}

		} else {
			// 如果不是静态内容，就看该url是否有对应的处理器
			BaseAction action = this.handlerMap.get(path);
			if (action != null) {
				try {
					String body = action.process(httpExchange);
					this.sendResponse(httpExchange, body);
				} catch (TemplateException e) {
					LogUtil.traceError(log, e);
				}
			} else {
				// 404
				httpExchange.sendResponseHeaders(404, 0);
			}
		}

		httpExchange.close();
	}

	/**
	 * 响应的是字节流
	 * 
	 * @param httpExchange
	 * @param sendBytes
	 * @throws IOException
	 */
	private void sendResponse(HttpExchange httpExchange, byte[] sendBytes) throws IOException {
		httpExchange.sendResponseHeaders(200, sendBytes.length);
		OutputStream os = httpExchange.getResponseBody();
		os.write(sendBytes);
		os.close();
	}

	/**
	 * 响应的是字符
	 * 
	 * @param httpExchange
	 * @param body
	 * @throws IOException
	 */
	private void sendResponse(HttpExchange httpExchange, String body) throws IOException {
		this.sendResponse(httpExchange, body.getBytes());
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
