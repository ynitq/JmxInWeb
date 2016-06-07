package com.ynitq.utils.jmxInWeb;

import java.io.IOException;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;

import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource;
import org.springframework.jmx.export.assembler.MetadataMBeanInfoAssembler;

import com.ynitq.utils.jmxInWeb.service.testMBeans.MBean1;
import com.ynitq.utils.jmxInWeb.spring.BaseSpringTemplate;

/**
 * 
 * <pre>
 * 通过Spring的实现类
 * </pre>
 * 
 * @author liangwj72
 * 
 */
public class TestJmxHttpServer extends BaseSpringTemplate {

	private final MBeanExporter mBeanExporter;

	public TestJmxHttpServer() {
		MBeanServer server = MBeanServerFactory.createMBeanServer("JmxInWeb");

		// 用注解方式定义mbean以及里面的属性
		MetadataMBeanInfoAssembler assembler = new MetadataMBeanInfoAssembler();
		assembler.setAttributeSource(new AnnotationJmxAttributeSource());

		this.mBeanExporter = new MBeanExporter();
		this.mBeanExporter.setAssembler(assembler);
		this.mBeanExporter.setServer(server);
	}

	@Override
	protected MBeanExporter getMBeanExporter() {
		return this.mBeanExporter;
	}

	@Override
	protected int getPort() {
		return 8089;
	}

	public static void main(String[] args) throws IOException {
		TestJmxHttpServer s = new TestJmxHttpServer();
		s.start();
		s.register(new MBean1());
	}

}
