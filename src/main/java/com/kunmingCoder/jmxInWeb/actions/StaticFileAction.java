package com.kunmingCoder.jmxInWeb.actions;

import java.io.IOException;

import com.kunmingCoder.jmxInWeb.AppConstants;
import com.kunmingCoder.jmxInWeb.utils.StringUtils;
import com.sun.net.httpserver.HttpExchange;

import freemarker.template.TemplateException;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 梁韦江
 * 2015年10月13日
 */
public class StaticFileAction extends BaseAction {

	@Override
	public void process(HttpExchange exchange) throws TemplateException, IOException {
		String path = exchange.getRequestURI().getPath();
		String fileName = path.substring(AppConstants.STATICS_PREFIX.length());
		byte[] sendBytes = StringUtils.loadFileFromClassPath(AppConstants.STATIC_RESOURCE_PREFIX + fileName);
		if (sendBytes == null) {
			// 404
			exchange.sendResponseHeaders(404, 0);
		} else {
			this.sendResponse(exchange, sendBytes);
		}
	}

	@Override
	public String getRequestUrl() {
		return null;
	}

}
