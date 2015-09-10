package com.kunmingCoder.jcweb;

import java.io.IOException;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 梁韦江
 * 2015年9月10日
 */
public class TestStartHttpServer {

	public static void main(String[] args) throws IOException {
		HttpAdaptor server = new HttpAdaptor();
		server.start();
		server.addAuthorization("1", "1");
	}
}
