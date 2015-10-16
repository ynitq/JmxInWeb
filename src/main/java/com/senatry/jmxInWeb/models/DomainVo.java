package com.senatry.jmxInWeb.models;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.management.MBeanInfo;
import javax.management.ObjectName;

/**
 * <pre>
 * 对Mbean进行分类显示用
 * </pre>
 * 
 * @author 梁韦江 2015年9月11日
 */
public class DomainVo implements Comparable<DomainVo> {
	private final String name;
	private final List<MBeanVo> beans = new LinkedList<MBeanVo>();

	private boolean sorted = false;

	public DomainVo(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<MBeanVo> getBeans() {
		if (!this.sorted) {
			this.sorted = true;
			Collections.sort(this.beans);
		}
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
