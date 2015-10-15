package com.senatry.jmxInWeb.service.testMBeans;

import java.lang.management.ManagementFactory;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

@ManagedResource(description = "测试")
@Service
public class MBean1 {

	private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(MBean1.class);

	private String prop1 = "prop1 value";

	@ManagedAttribute
	public String getAttrRW() {
		log.debug(String.format("getAttrRW() = %s", prop1));
		return prop1;
	}

	@ManagedAttribute(description = "读写属性")
	public void setAttrRW(String arg) {
		log.debug(String.format("setAttrRW(%s)", arg));
		this.prop1 = arg;
	}

	@ManagedAttribute(description = "只读属性")
	public Long getAttrReadOnly() {
		long res = System.currentTimeMillis();
		log.debug(String.format("getAttrReadOnly() = %d", res));

		return res;
	}

	@ManagedAttribute(description = "只写属性")
	public void setAttrWriteOnly(String arg) {
		log.debug(String.format("setAttrWriteOnly(%s)", arg));
	}

	@ManagedOperation(description = "对参数进行说明的例子")
	@ManagedOperationParameters(
	{
			@ManagedOperationParameter(description = "T1说明", name = "t1名字"),
			@ManagedOperationParameter(description = "T2说明", name = "t2名字")
	})
	public Long getCurTime(long t1,
			long t2) {

		return System.currentTimeMillis();
	}

	@ManagedOperation(description = "测试的获得MBeanServer")
	public String myMBeanServer() {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		// Set<ObjectName> set = mbs.queryNames(null, null);
		Set<ObjectInstance> set = mbs.queryMBeans(null, null);

		StringBuffer sb = new StringBuffer();

		for (ObjectInstance instance : set) {

			ObjectName name = instance.getObjectName();
			String domain = name.getDomain();

			String msg = String.format("domain=[%s] clazz=[%s] ",
					domain,
					instance.getClassName()
					);

			log.debug(msg);
			sb.append(msg);
		}
		return sb.toString();
	}
}
