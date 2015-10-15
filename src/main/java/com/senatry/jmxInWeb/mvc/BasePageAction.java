package com.senatry.jmxInWeb.mvc;

import java.io.IOException;
import java.util.Map;

import javax.management.JMException;

import com.senatry.jmxInWeb.http.MyHttpRequest;
import com.senatry.jmxInWeb.utils.LogUtil;

import freemarker.template.TemplateException;

/**
 * <pre>
 * 所有返回的页面的请求的基类
 * </pre>
 * 
 * @author 梁韦江 2015年10月14日
 */
public abstract class BasePageAction extends BaseAction {

	private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(BasePageAction.class);

	@Override
	public void process(MyHttpRequest request) throws IOException {
		Map<String, Object> dataModel = this.newModel();
		try {
			// 传入空的model，获得viewName，如果viewName不为空，就用freemark合成页面
			String viewName = this.getModelAndView(dataModel);
			if (viewName != null) {
				String body = TemplateService.getInstance().process(viewName, dataModel);
				request.sendResponse(body);
			}
		} catch (TemplateException e) {
			// TODO freemarker出错的时候要输出提示，
			LogUtil.traceError(log, e);
		} catch (JMException e) {
			// TODO JMException出错的时候要输出提示，
			LogUtil.traceError(log, e);
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
	protected abstract String getModelAndView(Map<String, Object> dataModel) throws TemplateException, IOException, JMException;

}