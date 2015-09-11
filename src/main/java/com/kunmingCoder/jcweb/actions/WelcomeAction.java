package com.kunmingCoder.jcweb.actions;

import com.kunmingCoder.jcweb.MBeanService;
import com.sun.net.httpserver.HttpExchange;

/**
 * <pre>
 * 欢迎页面
 * </pre>
 * 
 * @author 梁韦江 2015年9月11日
 */
public class WelcomeAction extends BaseAction {

	@Override
	public String process(HttpExchange exchange) {
		// TODO 欢迎页面

		MBeanService.getInstance().getAllMBaen();
		return "ok";
	}

	@Override
	public String getRequestUrl() {
		return "/";
	}

}
