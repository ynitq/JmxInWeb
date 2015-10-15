package com.senatry.jmxInWeb;

import java.io.IOException;

import com.senatry.jmxInWeb.SpingAnnotationHelper;
import com.senatry.jmxInWeb.http.HttpAdaptor;
import com.senatry.jmxInWeb.service.testMBeans.MBean1;

/**
 * 
 * <pre>
 * 测试启动一个jmx服务
 * </pre>
 * 
 * @author liangwj72
 * 
 */
public class TestJmxHttpServer {

	private final SpingAnnotationHelper helper = new SpingAnnotationHelper();

	public SpingAnnotationHelper getHelper() {
		return helper;
	}

	private HttpAdaptor httpAdaptor;

	/**
	 * 开始监听
	 * 
	 * @throws IOException
	 */
	public void start() throws IOException {

		this.httpAdaptor = new HttpAdaptor(this.helper.getMBeanServer());
		this.httpAdaptor.start();

		this.helper.register(this.httpAdaptor);
	}

	/**
	 * 停止HttpAdaptor
	 */
	protected void stopHttpAdaptor() {
		this.httpAdaptor.stop();
	}

	public static void main(String[] args) throws IOException {
		TestJmxHttpServer s = new TestJmxHttpServer();
		s.start();
		s.getHelper().register(new MBean1());
	}

}
