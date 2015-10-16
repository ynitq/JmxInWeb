package com.senatry.jmxInWeb.mvc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.senatry.jmxInWeb.actions.StaticFileAction;
import com.senatry.jmxInWeb.actions.mbean.AjaxChangeAttrAction;
import com.senatry.jmxInWeb.actions.mbean.MBeanInfoAction;
import com.senatry.jmxInWeb.actions.mbean.WelcomeAction;
import com.senatry.jmxInWeb.exception.BaseLogicException;
import com.senatry.jmxInWeb.http.MyHttpRequest;
import com.senatry.jmxInWeb.utils.LogUtil;
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

	private final Map<String, BaseAction> actionsMap = new HashMap<String, BaseAction>();

	private final BaseAction staticFileAction = new StaticFileAction();

	public ActionManager() {
		this.initActions();

		if (log.isDebugEnabled()) {
			log.debug(LogUtil.format("ActionManager inited, Total action:%d", this.actionsMap.size()));
		}
	}

	/**
	 * 将所有的action加到一个map中
	 */
	private void initActions() {
		// add all action to map
		this.addAction(new WelcomeAction());
		this.addAction(new MBeanInfoAction());
		this.addAction(new AjaxChangeAttrAction());
	}

	private void addAction(BaseAction handler) {
		this.actionsMap.put(handler.getRequestUrl(), handler);
	}

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {

		MyHttpRequest request = new MyHttpRequest(httpExchange);

		try {
			if (request.isStaticFileRequest()) {
				this.staticFileAction.process(request);
			} else {
				BaseAction action = this.actionsMap.get(request.getPath());
				if (action != null) {
					action.process(request);
				} else {
					// 404
					request.error404();
				}
			}
		} catch (BaseLogicException ex) {
			// TODO ACTION发生的异常要处理
			LogUtil.traceError(log, ex);
		}
		request.close();
	}
}
