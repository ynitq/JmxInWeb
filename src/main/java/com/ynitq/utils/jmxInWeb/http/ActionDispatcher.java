package com.ynitq.utils.jmxInWeb.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.ynitq.utils.jmxInWeb.actions.StaticFileAction;
import com.ynitq.utils.jmxInWeb.json.JsonErrorResponse;
import com.ynitq.utils.jmxInWeb.mvc.ActionContainer;
import com.ynitq.utils.jmxInWeb.mvc.BaseAction;
import com.ynitq.utils.jmxInWeb.mvc.BaseAjaxAction;
import com.ynitq.utils.jmxInWeb.mvc.BaseJsonResponse;
import com.ynitq.utils.jmxInWeb.utils.LogUtil;

/**
 * <pre>
 * HttpServer的请求分发处理器
 * 
 * 判断是否是静态资源，如果是静态资源就从jar包中返回该资源
 * 
 * 否则就根据url寻找对应的处理器，如果找不到处理器就返回默认的欢迎页面
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年9月11日
 */
public class ActionDispatcher implements HttpHandler {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ActionDispatcher.class);

	private final Map<String, BaseAction> actionsMap = new HashMap<String, BaseAction>();

	private final BaseAction staticFileAction = new StaticFileAction();

	public ActionDispatcher() {
		this.initActions();

		if (log.isDebugEnabled()) {
			log.debug(LogUtil.format("ActionManager inited, Total action:%d", this.actionsMap.size()));
		}
	}

	/**
	 * 将所有的action加到一个map中
	 */
	private void initActions() {

		//获得action的管理类
		IActionContainer actionManager = new ActionContainer();

		// add all action to map
		List<BaseAction> list = actionManager.getAllActions();
		for (BaseAction handler : list) {

			this.actionsMap.put(handler.getRequestUrl(), handler);

			if (log.isDebugEnabled()) {
				log.debug(LogUtil.format("path=%s\taction=%s", handler.getRequestUrl(), handler.getClass().getName()));
			}
		}

	}

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		MyHttpRequest request = new MyHttpRequest(httpExchange);

		if (request.isStaticFileRequest()) {
			// static files
			try {
				this.staticFileAction.process(request);
			} catch (Throwable ex) {
				LogUtil.traceError(log, ex);
			}
		} else {
			BaseAction action = this.actionsMap.get(request.getPath());
			if (action != null) {
				try {
					// process request
					action.process(request);
				} catch (Throwable ex) {
					// trace error to log
					LogUtil.traceError(log, ex);

					if (action instanceof BaseAjaxAction) {
						// ajax action
						BaseJsonResponse res = new JsonErrorResponse(ex);
						String body = JSON.toJSONString(res);
						request.sendResponse(body);
					} else {
						// TODO 处理页面的502错误
						String body = ex.getMessage();
						request.sendResponse(body);
					}
				}
			} else {
				// action not found
				request.error404();
			}
		}
		request.close();
	}
}
