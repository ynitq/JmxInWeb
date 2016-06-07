package com.ynitq.utils.jmxInWeb.spring;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.springframework.jmx.export.MBeanExporter;

import com.ynitq.utils.jmxInWeb.http.HttpAdaptor;
import com.ynitq.utils.jmxInWeb.service.MBeanUtil;

/**
 * <pre>
 * 基础的对Spring的支持，主要是对spring以注解方式什么MBean的支持
 * </pre>
 * 
 * @author 梁韦江 2016年6月7日
 */
public abstract class BaseSpringTemplate {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BaseSpringTemplate.class);

	private HttpAdaptor httpAdaptor;

	public MBeanServer getMBeanServer() {
		return this.getMBeanExporter().getServer();
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
		if (!this.isEnable()) {
			return;
		}

		try {
			ObjectName oname = ObjectName.getInstance(domain + ":name=" + name);
			if (!this.getMBeanServer().isRegistered(oname)) {
				this.getMBeanExporter().registerManagedResource(obj, oname);
			}
		} catch (Exception e) {
			log.error("jmx注册失败", e);
		}
	}

	@PreDestroy
	public void shutdown() {

		if (this.httpAdaptor != null) {
			this.httpAdaptor.stop();
			log.debug("JmxInWeb 停止了");
		}
	}

	@PostConstruct
	public void start() throws IOException {

		MBeanUtil.getInstance().setDomainNameFilter(new DefaultDomainNameFilter());

		if (!this.isEnable()) {
			log.info("jmx功能未开启");
		} else {
			this.httpAdaptor = new HttpAdaptor(this.getMBeanServer());
			this.httpAdaptor.setPort(this.getPort());

			this.register(this.httpAdaptor);

			this.httpAdaptor.start();

			log.debug("Jmx 管理界面 start on http://localhost:{}", this.getPort());
		}
	}

	public void unRegister(Object obj) {
		if (obj != null) {
			Class<?> clazz = obj.getClass();
			this.unRegister(clazz.getPackage().getName(), clazz.getSimpleName());
		}
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
		if (!this.isEnable()) {
			return;
		}

		try {
			ObjectName oname = ObjectName.getInstance(domain + ":name=" + name);
			if (this.getMBeanServer().isRegistered(oname)) {
				this.getMBeanServer().unregisterMBean(oname);
			}
		} catch (Exception e) {
			log.error("jmx注销注册失败", e);
		}
	}

	private boolean isEnable() {
		return this.getMBeanExporter() != null;
	}

	protected abstract MBeanExporter getMBeanExporter();

	/**
	 * 监听的端口
	 * 
	 * @return
	 */
	protected abstract int getPort();
}
