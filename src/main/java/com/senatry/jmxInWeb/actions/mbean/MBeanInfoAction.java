package com.senatry.jmxInWeb.actions.mbean;

import java.io.IOException;
import java.util.Map;

import javax.management.JMException;

import com.senatry.jmxInWeb.mvc.BasePageAction;

import freemarker.template.TemplateException;

/**
 * <pre>
 * 获得mbean的信息
 * </pre>
 * 
 * @author 梁韦江 2015年10月15日
 */
public class MBeanInfoAction extends BasePageAction {

	@Override
	public String getRequestUrl() {
		return "mbean";
	}

	@Override
	protected String getModelAndView(Map<String, Object> dataModel) throws TemplateException, IOException, JMException {
		// TODO Auto-generated method stub
		return "mbeanInfo.html";
	}

}
