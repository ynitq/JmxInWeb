package com.senatry.jmxInWeb.actions.mbean;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.senatry.jmxInWeb.http.MyHttpRequest;
import com.senatry.jmxInWeb.models.DomainVo;
import com.senatry.jmxInWeb.mvc.BasePageAction;
import com.senatry.jmxInWeb.service.MBeanService;

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
	protected String getModelAndView(MyHttpRequest request, Map<String, Object> dataModel) throws IOException {
		List<DomainVo> list = MBeanService.getInstance().getAllMBaen();
		dataModel.put("list", list);
		return "index.html";
	}

}
