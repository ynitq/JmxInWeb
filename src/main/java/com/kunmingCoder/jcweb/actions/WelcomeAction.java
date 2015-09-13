package com.kunmingCoder.jcweb.actions;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.kunmingCoder.jcweb.models.DomainVo;
import com.kunmingCoder.jcweb.service.MBeanService;
import com.sun.net.httpserver.HttpExchange;

import freemarker.template.TemplateException;

/**
 * <pre>
 * 欢迎页面
 * </pre>
 * 
 * @author 梁韦江 2015年9月11日
 */
public class WelcomeAction extends BaseAction {

	@Override
	public String process(HttpExchange exchange) throws TemplateException, IOException {
		List<DomainVo> list = MBeanService.getInstance().getAllMBaen();
		Map<String, Object> dataModel = this.newModel();
		dataModel.put("list", list);
		
		return this.genViewPage("index.html", dataModel);
	}

	@Override
	public String getRequestUrl() {
		return "/";
	}

}
