package com.senatry.jmxInWeb.actions.mbean;

import java.io.IOException;
import java.util.Map;

import javax.management.JMException;

import com.senatry.jmxInWeb.exception.BaseLogicException;
import com.senatry.jmxInWeb.http.MyHttpRequest;
import com.senatry.jmxInWeb.json.JsonCommonResponse;
import com.senatry.jmxInWeb.mvc.BaseAjaxAction;
import com.senatry.jmxInWeb.mvc.BaseJsonResponse;
import com.senatry.jmxInWeb.service.MBeanService;

/**
 * <pre>
 * ajaxChangeAttr
 * </pre>
 * 
 * @author 梁韦江 2015年10月16日
 */
public class AjaxChangeAttrAction extends BaseAjaxAction {

	@Override
	protected BaseJsonResponse getJsonResponse(MyHttpRequest request, Map<String, Object> dataModel)
			throws IOException, BaseLogicException, JMException {
		// TODO getJsonResponse
		MBeanForm form = request.bindForm(MBeanForm.class);
		form.verifyForChangeAttrValue();

		MBeanService.getInstance().changeAttrValue(form);
		
		JsonCommonResponse res = new JsonCommonResponse();

		return res;
	}

	@Override
	public String getRequestUrl() {
		return "/ajaxChangeAttr";
	}

}
