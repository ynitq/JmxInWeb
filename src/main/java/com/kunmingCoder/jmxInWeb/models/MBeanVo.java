package com.kunmingCoder.jmxInWeb.models;

import javax.management.MBeanInfo;
import javax.management.ObjectName;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 梁韦江
 * 2015年9月11日
 */
public class MBeanVo {
	private final MBeanInfo mBeanInfo;
	private final ObjectName targetName;

	public MBeanVo(ObjectName targetName, MBeanInfo mBeanInfo) {
		super();
		this.targetName = targetName;
		this.mBeanInfo = mBeanInfo;
	}

	public String getName() {
		return this.targetName.getCanonicalName();
	}

	public String getDesc() {
		return this.mBeanInfo.getDescription();
	}

	public String getClassName() {
		return this.mBeanInfo.getClassName();
	}
}
