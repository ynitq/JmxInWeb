package com.kunmingCoder.jmxInWeb.models;

import java.util.LinkedList;
import java.util.List;

import javax.management.MBeanInfo;
import javax.management.ObjectName;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 梁韦江 2015年9月11日
 */
public class DomainVo implements Comparable<DomainVo> {
	private final String name;
	private final List<MBeanVo> beans = new LinkedList<MBeanVo>();

	public DomainVo(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<MBeanVo> getBeans() {
		return beans;
	}

	public void addMBean(ObjectName name, MBeanInfo info) {
		MBeanVo vo = new MBeanVo(name, info);
		this.beans.add(vo);
	}

	@Override
	public int compareTo(DomainVo other) {
		return this.name.compareTo(other.name);
	}

}
