package com.kunmingCoder.jmxInWeb.actions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.kunmingCoder.jmxInWeb.service.TemplateService;

import freemarker.template.TemplateException;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 梁韦江
 * 2015年9月11日
 */
public abstract class BaseAction implements IRequestHandler {

	public abstract String getRequestUrl();

	protected Map<String, Object> newModel() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}

	protected String genViewPage(String viewName, Map<String, Object> dataModel) throws TemplateException, IOException {
		return TemplateService.getInstance().process(viewName, dataModel);
	}

}
