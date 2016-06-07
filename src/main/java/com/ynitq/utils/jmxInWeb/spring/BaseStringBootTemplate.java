package com.ynitq.utils.jmxInWeb.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.MBeanExporter;

/**
 * <pre>
 * 对 SpringBoot的支持, Spring boot 自带jmx支持，所以直接用就行了
 * </pre>
 * 
 * @author 梁韦江 2016年6月7日
 */
public abstract class BaseStringBootTemplate extends BaseSpringTemplate{

	@Autowired(required = false)
	private MBeanExporter mBeanExporter;

	@Override
	protected MBeanExporter getMBeanExporter() {
		return this.mBeanExporter;
	}

}
