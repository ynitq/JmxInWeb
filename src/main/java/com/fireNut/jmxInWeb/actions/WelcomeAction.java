package com.fireNut.jmxInWeb.actions;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fireNut.jmxInWeb.http.BasePageAction;
import com.fireNut.jmxInWeb.models.DomainVo;
import com.fireNut.jmxInWeb.service.MBeanService;

import freemarker.template.TemplateException;

/**
 * <pre>
 * 欢迎页面
 * </pre>
 * 
 * @author 梁韦江 2015年9月11日
 */
public class WelcomeAction extends BasePageAction {

	@Override
	public String getRequestUrl() {
		return "/";
	}

	@Override
	protected String getViewName() {
		return "index.html";
	}

	@Override
	protected void fillDataModel(Map<String, Object> dataModel) throws TemplateException, IOException {
		List<DomainVo> list = MBeanService.getInstance().getAllMBaen();
		dataModel.put("list", list);
	}

}
