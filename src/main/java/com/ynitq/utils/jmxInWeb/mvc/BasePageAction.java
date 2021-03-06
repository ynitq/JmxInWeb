package com.ynitq.utils.jmxInWeb.mvc;

import java.io.IOException;
import java.util.Map;

import javax.management.JMException;

import com.ynitq.utils.jmxInWeb.exception.BaseLogicException;
import com.ynitq.utils.jmxInWeb.exception.MyFreeMarkerException;
import com.ynitq.utils.jmxInWeb.exception.MyJmException;
import com.ynitq.utils.jmxInWeb.http.MyHttpRequest;

import freemarker.template.TemplateException;

/**
 * <pre>
 * 所有返回的页面的请求的基类
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年10月14日
 */
public abstract class BasePageAction extends BaseAction {

	@Override
	public void process(MyHttpRequest request) throws IOException, BaseLogicException {
		Map<String, Object> dataModel = this.newModel();
		try {
			// 传入空的model，获得viewName，如果viewName不为空，就用freemark合成页面
			String viewName = this.getModelAndView(request, dataModel);
			if (viewName != null) {
				String body = TemplateService.getInstance().process(viewName, dataModel);
				request.sendResponse(body);
			}
		} catch (TemplateException e) {
			throw new MyFreeMarkerException(e);
		} catch (JMException e) {
			throw new MyJmException(e);
		}

	}

	/**
	 * <pre>
	 * 填充数据并返回view name
	 * 要处理404或者302时，就返回null
	 * </pre>
	 * 
	 * @param dataModel
	 * @return
	 * @throws TemplateException
	 * @throws IOException
	 */
	protected abstract String getModelAndView(MyHttpRequest request, Map<String, Object> dataModel)
			throws IOException, JMException, BaseLogicException;

}
