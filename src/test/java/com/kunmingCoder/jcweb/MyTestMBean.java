package com.kunmingCoder.jcweb;

import java.lang.management.ManagementFactory;
import java.util.Set;

import javax.management.MBeanRegistration;
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
public class MyTestMBean {

	private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(MyTestMBean.class);

	private String prop1 = "prop1 value";

	@ManagedAttribute(description = "属性1")
	public String getProp1() {
		return prop1;
	}

	@ManagedAttribute
	public void setProp1(String prop1) {
		this.prop1 = prop1;
	}

	@ManagedOperation(description = "测试的op")
	public Long getCurTime111() {

		return System.currentTimeMillis();
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

	/**
	 * <pre>
	 * TODO TO 廖望舒， 如何获得mbean的说明和例子 by 梁韦江
	 *  
	 * 可以通过 ManagementFactory获得虚拟机本身的各类MBeanServer, 例如内存使用情况、硬件配置、线程情况等等
	 * 每一类的MBean都注册在不同的MBeanServer中
	 * 
	 * 但ManagementFactory无法获得当前应用程序的MBeanServer，至少我不知道怎么弄
	 * 
	 * 如果需要获得当前应用的MBeanServer，可参照HttpAdaptor，原理就是我们自己写个MBean，
	 * 我们将这个MBean注册到当前应用程序的MBeanServer，然后在这个MBean的注册过程中获得当前的MBeanServer
	 * 然后我们就可以通过 MBeanServer搜索到所有的注册的MBean，自然就可以对这些MBean进行各类操作
	 * 
	 * 这个自己写的MBean必须遵循原始的MBean的写法，必须实现MBeanRegistration这个接口，而不能通过Spring偷工减料实现MBean
	 * 
	 * </pre>
	 * 
	 * @see HttpAdaptor#preRegister(MBeanServer, ObjectName)
	 *      HttpAdaptor里面这个例子就通过在注册mbean时调用的方法中获得MBeanServer
	 * 
	 * @see MBeanRegistration
	 *      这个MBeanRegistration的接口中有preRegister这个方法，可获得MBeanServer
	 * 
	 * @see MBeanServer#queryMBeans(ObjectName, javax.management.QueryExp)
	 *      queryMBeans这个方法当参数都为NULL时，就可以在该MBeanServer获得所有注册的MBean，下面的代码有例子
	 */
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
