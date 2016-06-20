package com.ynitq.utils.jmxInWeb.models;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.management.JMException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * <pre>
 * 对MBeanInfo的包装
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年9月11日
 */
public class MBeanVo implements Comparable<MBeanVo> {
	private final MBeanInfo info;
	private final ObjectName targetName;
	private final List<MBeanAttrVo> attrs = new LinkedList<MBeanAttrVo>();
	private final List<MBeanOpVo> opts = new LinkedList<MBeanOpVo>();

	private String displayName;// 显示用的名字

	public MBeanVo(ObjectName targetName, MBeanInfo mBeanInfo) {
		super();
		this.targetName = targetName;
		this.info = mBeanInfo;

		MBeanAttributeInfo[] attrArray = mBeanInfo.getAttributes();
		if (attrArray != null) {
			for (MBeanAttributeInfo mBeanAttributeInfo : attrArray) {
				this.attrs.add(new MBeanAttrVo(mBeanAttributeInfo));
			}
			Collections.sort(this.attrs);
		}

		MBeanOperationInfo[] operations = mBeanInfo.getOperations();
		if (operations != null) {
			for (MBeanOperationInfo mBeanOperationInfo : operations) {
				if (isOperationRole(mBeanOperationInfo)) {
					// 只有role = "operation"才需要加进来，role是setter或者getter的，都是属性的
					this.opts.add(new MBeanOpVo(mBeanOperationInfo));
				}
			}
			Collections.sort(this.opts);
		}
	}

	public List<MBeanAttrVo> getAttrs() {
		return attrs;
	}

	public String getClassName() {
		return this.info.getClassName();
	}

	public String getDesc() {
		return this.info.getDescription();
	}

	public String getObjectName() {
		return this.targetName.getCanonicalName();
	}

	/**
	 * 获得显示用名字
	 * 
	 * @return
	 */
	public String getDisplayName() {
		if (this.displayName == null) {
			String oname = this.targetName.getCanonicalName();
			int index = oname.indexOf("name=");
			if (index >= 0) {
				this.displayName = oname.substring(index + 5);
			} else {
				this.displayName = oname;
			}
		}
		return this.displayName;
	}

	public List<MBeanOpVo> getOpts() {
		return opts;
	}

	public void setValueFromMBeanServer(MBeanServer server) throws JMException {
		for (MBeanAttrVo attrVo : attrs) {
			attrVo.setValueFromMBeanServer(targetName, server);
		}
	}

	/**
	 * 判断一个操作的role是否是operation
	 * 
	 * @param info
	 * @return
	 */
	private boolean isOperationRole(MBeanOperationInfo info) {
		Object value = info.getDescriptor().getFieldValue("role");
		return value != null && "operation".equals(value.toString());
	}

	@Override
	public int compareTo(MBeanVo o) {
		return this.getDisplayName().compareToIgnoreCase(o.getDisplayName());
	}
}
