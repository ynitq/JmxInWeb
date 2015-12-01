package com.ynitq.utils.jmxInWeb.actions.mbean;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ynitq.utils.jmxInWeb.http.MyHttpRequest;
import com.ynitq.utils.jmxInWeb.models.DomainVo;
import com.ynitq.utils.jmxInWeb.mvc.BasePageAction;
import com.ynitq.utils.jmxInWeb.service.MBeanService;

/**
 * <pre>
 * 欢迎页面
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年9月11日
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
