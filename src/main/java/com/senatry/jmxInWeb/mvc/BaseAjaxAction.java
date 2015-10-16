package com.senatry.jmxInWeb.mvc;

import java.io.IOException;
import java.util.Map;

import javax.management.JMException;

import com.alibaba.fastjson.JSON;
import com.senatry.jmxInWeb.exception.BaseLogicException;
import com.senatry.jmxInWeb.exception.MyJmException;
import com.senatry.jmxInWeb.http.MyHttpRequest;
import com.senatry.jmxInWeb.json.JsonErrorResponse;

import freemarker.template.TemplateException;

/**
 * <pre>
 * 所有返回的页面的请求的基类
 * </pre>
 * 
 * @author 梁韦江 2015年10月14日
 */
public abstract class BaseAjaxAction extends BaseAction {

	@Override
	public void process(MyHttpRequest request) throws IOException {
		// new empty dataModel
		Map<String, Object> dataModel = this.newModel();

		// get json response
		BaseJsonResponse res;
		try {
			res = this.getJsonResponse(request, dataModel);
			if (res == null) {
				res = new BaseJsonResponse();
			}
		} catch (BaseLogicException e) {
			res = new JsonErrorResponse(e);
		} catch (JMException ex) {
			res = new JsonErrorResponse(new MyJmException(ex));
		}

		String body = JSON.toJSONString(res);
		request.sendResponse(body);
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
	 * @throws JMException
	 */
	protected abstract BaseJsonResponse getJsonResponse(MyHttpRequest request, Map<String, Object> dataModel)
			throws IOException, BaseLogicException, JMException;

}
