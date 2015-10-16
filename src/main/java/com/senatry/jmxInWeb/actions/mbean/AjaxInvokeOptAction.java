package com.senatry.jmxInWeb.actions.mbean;

import java.io.IOException;
import java.util.Map;

import javax.management.JMException;

import com.senatry.jmxInWeb.exception.BaseLogicException;
import com.senatry.jmxInWeb.http.MyHttpRequest;
import com.senatry.jmxInWeb.json.JsonCommonResponse;
import com.senatry.jmxInWeb.mvc.BaseAjaxAction;
import com.senatry.jmxInWeb.mvc.BaseJsonResponse;

/**
 * <pre>
 * invokeOpt
 * 
 * </pre>
 * 
 * @author 梁韦江 2015年10月16日
 */
public class AjaxInvokeOptAction extends BaseAjaxAction {

	@Override
	protected BaseJsonResponse getJsonResponse(MyHttpRequest request, Map<String, Object> dataModel)
			throws IOException, BaseLogicException, JMException {

		AjaxInvokeOptForm form = request.bindForm(AjaxInvokeOptForm.class);
		form.verify();

		JsonCommonResponse res = new JsonCommonResponse();
		res.setData("data");

		return res;
	}

	@Override
	public String getRequestUrl() {
		return "/invokeOpt";
	}

}
