package com.senatry.jmxInWeb.models;

import java.util.LinkedList;
import java.util.List;

import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;

/**
 * <pre>
 * 包装MBeanOperationInfo
 * </pre>
 * 
 * @author 梁韦江 2015年10月15日
 */
public class MBeanOptVo implements Comparable<MBeanOptVo> {
	private final MBeanOperationInfo info;

	private final List<MBeanOptParamVo> params = new LinkedList<MBeanOptParamVo>();

	private final boolean invokable;

	public MBeanOptVo(MBeanOperationInfo info) {
		super();
		this.info = info;

		boolean canInput = true;

		MBeanParameterInfo[] paramArray = info.getSignature();
		for (MBeanParameterInfo paramInfo : paramArray) {
			MBeanOptParamVo paramVo = new MBeanOptParamVo(paramInfo);
			this.params.add(paramVo);

			// 如果所有的属性都可以输入，这个方法才可以在界面上被调用
			canInput = canInput && paramVo.isInputable();
		}
		this.invokable = canInput;
	}

	public MBeanOperationInfo getInfo() {
		return info;
	}

	public List<MBeanOptParamVo> getParams() {
		return params;
	}

	/**
	 * 是否可在界面上调用（所有参数都需要可输入）
	 * 
	 * @return
	 */
	public boolean isInvokable() {
		return invokable;
	}

	/**
	 * 是否没用参数
	 * 
	 * @return
	 */
	public boolean isNoParam() {
		return this.params.isEmpty();
	}

	public Object toDebugString() {

		return info.toString();
	}

	@Override
	public int compareTo(MBeanOptVo o) {
		return this.info.getName().compareToIgnoreCase(o.info.getName());
	}

}
