package com.senatry.jmxInWeb.service.testMBeans;

import java.util.Date;

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

	private String attrRwString = "attrWr value";

	private long attrRwLong;

	private boolean attrRwBoolean;

	private Date attrRwDate;

	@ManagedAttribute
	public String getAttrRwString() {
		log.debug(String.format("getAttrRW() = %s", attrRwString));
		return attrRwString;
	}

	@ManagedAttribute(description = "读写属性String")
	public void setAttrRwString(String arg) {
		log.debug(String.format("setAttrRW(%s)", arg));
		this.attrRwString = arg;
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

	@ManagedOperation(description = "对参数进行说明的操作")
	@ManagedOperationParameters(
	{
			@ManagedOperationParameter(description = "T1说明", name = "t1名字"),
			@ManagedOperationParameter(description = "T2说明", name = "t2名字"),
			@ManagedOperationParameter(description = "T3说明", name = "t3名字")
	})
	public Long opMultiParam(long t1, String t2, boolean t3) {

		log.debug(String.format("opMultiParam(%d, %s, %s)", t1, t2, t3));

		return System.currentTimeMillis();
	}

	@ManagedOperation(description = "无参数的操作")
	public String opNoParam() {
		// MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

		log.debug("opNoParam()");

		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < 500; i++) {
			buff.append("opNoParam ");
		}

		return buff.toString();
	}

	@ManagedOperation(description = "无返回的操作")
	public void opNoReturn() {
		log.debug("opNoReturn()");
	}

	@ManagedAttribute(description = "读写属性long")
	public long getAttrRwLong() {
		return attrRwLong;
	}

	@ManagedAttribute
	public void setAttrRwLong(long attrRwLong) {
		this.attrRwLong = attrRwLong;
	}

	@ManagedAttribute(description = "读写属性Boolean")
	public boolean isAttrRwBoolean() {
		return attrRwBoolean;
	}

	@ManagedAttribute
	public void setAttrRwBoolean(boolean attrRwBoolean) {
		this.attrRwBoolean = attrRwBoolean;
	}

	@ManagedAttribute(description = "读写属性Date")
	public Date getAttrRwDate() {
		return attrRwDate;
	}

	@ManagedAttribute
	public void setAttrRwDate(Date attrRwDate) {
		this.attrRwDate = attrRwDate;
	}
}
