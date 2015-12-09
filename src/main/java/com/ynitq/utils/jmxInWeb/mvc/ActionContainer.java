package com.ynitq.utils.jmxInWeb.mvc;

import java.util.LinkedList;
import java.util.List;

import com.ynitq.utils.jmxInWeb.actions.mbean.AjaxChangeAttrAction;
import com.ynitq.utils.jmxInWeb.actions.mbean.AjaxInvokeOpAction;
import com.ynitq.utils.jmxInWeb.actions.mbean.MBeanInfoAction;
import com.ynitq.utils.jmxInWeb.actions.mbean.WelcomeAction;
import com.ynitq.utils.jmxInWeb.http.IActionContainer;

/**
 * <pre>
 * 维护所有的action。
 * 
 * 每次我们增加一个action的时候，我们都需要在这个地方将实例添加带list中
 * </pre>
 * 
 * @author <a href="https://github.com/liangwj72">Alex (梁韦江)</a>
 * 2015年12月9日
 */
public class ActionContainer implements IActionContainer {

	@Override
	public List<BaseAction> getAllActions() {
		
		List<BaseAction> all = new LinkedList<BaseAction>();
		
		all.addAll(this.getMBeanActions());
		
		return all;
	}
	
	/**
	 * 和MBean相关的
	 * @return
	 */
	private List<BaseAction> getMBeanActions() {
		List<BaseAction> list = new LinkedList<BaseAction>();

		list.add(new WelcomeAction());
		list.add(new MBeanInfoAction());
		list.add(new AjaxChangeAttrAction());
		list.add(new AjaxInvokeOpAction());

		return list;
		
	}

}
