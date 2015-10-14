package com.fireNut.jmxInWeb.http;

import java.io.IOException;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 梁韦江 2015年9月11日
 */
public interface IRequestHandler {
	abstract void process(MyHttpRequest request) throws IOException;

}
