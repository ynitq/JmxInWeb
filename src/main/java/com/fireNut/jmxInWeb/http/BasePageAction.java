package com.fireNut.jmxInWeb.http;

import java.io.IOException;
import java.util.Map;

import com.fireNut.jmxInWeb.service.TemplateService;
import com.fireNut.jmxInWeb.utils.LogUtil;

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
			this.fillDataModel(dataModel);
			String body = TemplateService.getInstance().process(this.getViewName(), dataModel);
			request.sendResponse(body);
		} catch (TemplateException e) {
			// TODO 出错的时候要输出提示，
			LogUtil.traceError(log, e);
		}

	}

	/**
	 * 文件名
	 * 
	 * @return
	 */
	protected abstract String getViewName();

	/**
	 * 填充数据
	 * 
	 * @param dataModel
	 * @return
	 * @throws TemplateException
	 * @throws IOException
	 */
	protected abstract void fillDataModel(Map<String, Object> dataModel) throws TemplateException, IOException;

}
