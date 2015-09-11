package com.kunmingCoder.jcweb.actions;

import com.sun.net.httpserver.HttpExchange;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 梁韦江 2015年9月11日
 */
public interface IRequestHandler {
	abstract String process(HttpExchange exchange);

}
