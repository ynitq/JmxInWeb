package com.kunmingCoder.jmxInWeb.actions;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import freemarker.template.TemplateException;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 梁韦江 2015年9月11日
 */
public interface IRequestHandler {
	abstract String process(HttpExchange exchange) throws TemplateException, IOException;

}
