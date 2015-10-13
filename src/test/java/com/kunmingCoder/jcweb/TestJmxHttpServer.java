package com.kunmingCoder.jcweb;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource;
import org.springframework.jmx.export.assembler.MetadataMBeanInfoAssembler;
import org.springframework.jmx.support.MBeanServerFactoryBean;

import com.kunmingCoder.jmxInWeb.http.HttpAdaptor;
import com.kunmingCoder.jmxInWeb.utils.LogUtil;

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

	private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(TestJmxHttpServer.class);

	private MBeanExporter mBeanExporter;

	private HttpAdaptor httpAdaptor;

	private boolean inited;

	/**
	 * 开始监听
	 * 
	 * @throws IOException
	 */
	public void start() throws IOException {

		MBeanServer server = this.getMBeanServer();

		// 用注解方式定义mbean以及里面的属性
		MetadataMBeanInfoAssembler assembler = new MetadataMBeanInfoAssembler();
		assembler.setAttributeSource(new AnnotationJmxAttributeSource());

		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("system:name=HttpAdaptor", this.httpAdaptor);

		this.mBeanExporter = new MBeanExporter();
		this.mBeanExporter.setBeans(beans);
		this.mBeanExporter.setAssembler(assembler);
		this.mBeanExporter.setServer(server);

		this.httpAdaptor = new HttpAdaptor(server);
		this.httpAdaptor.start();

		this.inited = true;
	}

	private MBeanServer getMBeanServer() {
		MBeanServerFactoryBean mBeanServerFactory = new MBeanServerFactoryBean();
		mBeanServerFactory.afterPropertiesSet();
		return mBeanServerFactory.getObject();
	}

	/**
	 * 反注册一个mbean
	 * 
	 * @param domain
	 *            分类名
	 * @param name
	 *            mbean名
	 */
	public void unRegister(String domain, String name) {
		if (!this.inited) {
			return;
		}

		try {
			ObjectName oname = ObjectName.getInstance(domain + ":name=" + name);
			if (mBeanExporter.getServer().isRegistered(oname)) {
				mBeanExporter.getServer().unregisterMBean(oname);
			}
		} catch (Exception e) {
			LogUtil.traceError(log, e, "jmx注销注册失败");
		}
	}

	public void unRegister(Object obj) {
		if (obj != null) {
			Class<?> clazz = obj.getClass();
			this.unRegister(clazz.getPackage().getName(), clazz.getSimpleName());
		}
	}

	/**
	 * 注册一个mbean
	 * 
	 * @param obj
	 *            mbean的对象
	 * @param domain
	 *            分类名
	 * @param name
	 *            mbean名
	 */
	public void register(Object obj, String domain, String name) {
		if (!this.inited) {
			return;
		}

		try {
			ObjectName oname = ObjectName.getInstance(domain + ":name=" + name);
			if (!mBeanExporter.getServer().isRegistered(oname)) {
				mBeanExporter.registerManagedResource(obj, oname);
			}
		} catch (Exception e) {
			LogUtil.traceError(log, e, "jmx注册失败");
		}
	}

	/**
	 * 注册一个mbean，默认的domain是obj的包名，名字是类名
	 * 
	 * @param obj
	 */
	public void register(Object obj) {
		if (obj != null) {
			Class<?> clazz = obj.getClass();
			this.register(obj, clazz.getPackage().getName(), clazz.getSimpleName());
		}
	}

	/**
	 * 注册一个mbean，默认名字是类名
	 * 
	 * @param obj
	 */
	public void register(Object obj, String domain) {
		if (obj != null) {
			Class<?> clazz = obj.getClass();
			this.register(obj, domain, clazz.getSimpleName());
		}
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
		s.register(new MyTestMBean());
	}

}
