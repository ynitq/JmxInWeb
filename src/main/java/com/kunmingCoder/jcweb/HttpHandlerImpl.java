package com.kunmingCoder.jcweb;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kunmingCoder.jcweb.actions.BaseAction;
import com.kunmingCoder.jcweb.actions.IRequestHandler;
import com.kunmingCoder.jcweb.actions.WelcomeAction;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

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

	private final IRequestHandler welcomeAction = new WelcomeAction();

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
			byte[] sendBytes = this.loadStaticFile(fileName);
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
				String body = action.process(httpExchange);
				this.sendResponse(httpExchange, body);
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

	/**
	 * 从文件中获取内容
	 * 
	 * @param fileName
	 *            文件名
	 * @return
	 * @throws IOException
	 */
	private byte[] loadStaticFile(String fileName) throws IOException {
		InputStream is = ClassLoader.getSystemResourceAsStream(AppConstants.STATIC_RESOURCE_PREFIX + fileName);
		if (is != null) {
			ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
			byte[] buf = new byte[4096];
			int len;
			while ((len = is.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			byte[] bytes = out.toByteArray();
			return bytes;
		} else {
			return null;
		}
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
