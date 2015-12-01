package com.ynitq.utils.jmxInWeb;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource;
import org.springframework.jmx.export.assembler.MetadataMBeanInfoAssembler;

import com.ynitq.utils.jmxInWeb.utils.LogUtil;

/**
 * 
 * <pre>
 * 通过Spring的实现类
 * </pre>
 * 
 * @author liangwj72
 * 
 */
public class SimpleSpingSupport {

	private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(SimpleSpingSupport.class);

	private final MBeanExporter mBeanExporter;

	public SimpleSpingSupport() {
		MBeanServer server = MBeanServerFactory.createMBeanServer("JmxInWeb");

		// 用注解方式定义mbean以及里面的属性
		MetadataMBeanInfoAssembler assembler = new MetadataMBeanInfoAssembler();
		assembler.setAttributeSource(new AnnotationJmxAttributeSource());

		this.mBeanExporter = new MBeanExporter();
		this.mBeanExporter.setAssembler(assembler);
		this.mBeanExporter.setServer(server);
	}

	public MBeanServer getMBeanServer() {
		return this.mBeanExporter.getServer();
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
}
