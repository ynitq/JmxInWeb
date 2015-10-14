package com.fireNut.jmxInWeb.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fireNut.jmxInWeb.actions.StaticFileAction;
import com.fireNut.jmxInWeb.actions.WelcomeAction;
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
public class ActionManager implements HttpHandler {

	private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(ActionManager.class);


	private final Map<String, BaseAction> handlerMap = new HashMap<String, BaseAction>();

	private final BaseAction staticFileAction = new StaticFileAction();

	public ActionManager() {
		if (log.isDebugEnabled()) {
			log.debug("HttpHandlerImpl init");
		}

		// add all action to map
		this.addHandler(new WelcomeAction());
	}

	private void addHandler(BaseAction handler) {
		this.handlerMap.put(handler.getRequestUrl(), handler);

	}

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		MyHttpRequest request = new MyHttpRequest(httpExchange);

		if (request.isStaticFileRequest()) {
			this.staticFileAction.process(request);
		} else {
			BaseAction action = this.handlerMap.get(request.getPath());
			if (action != null) {
				action.process(request);
			} else {
				// 404
				request.error404();
			}
		}
		request.close();
	}
}
