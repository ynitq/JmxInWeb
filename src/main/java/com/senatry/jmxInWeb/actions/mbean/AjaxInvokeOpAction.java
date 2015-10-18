package com.senatry.jmxInWeb.actions.mbean;

import java.io.IOException;
import java.util.Map;

import javax.management.JMException;

import com.senatry.jmxInWeb.exception.BaseLogicException;
import com.senatry.jmxInWeb.http.MyHttpRequest;
import com.senatry.jmxInWeb.json.JsonInvokeOptResponse;
import com.senatry.jmxInWeb.mvc.BaseAjaxAction;
import com.senatry.jmxInWeb.mvc.BaseJsonResponse;
import com.senatry.jmxInWeb.service.MBeanService;

/**
 * <pre>
 * invoke Op
 * 
 * </pre>
 * 
 * @author 梁韦江 2015年10月16日
 */
public class AjaxInvokeOpAction extends BaseAjaxAction {

	@Override
	protected BaseJsonResponse getJsonResponse(MyHttpRequest request, Map<String, Object> dataModel)
			throws IOException, BaseLogicException, JMException {

		AjaxInvokeOpForm form = request.bindForm(AjaxInvokeOpForm.class);
		form.verify();

		JsonInvokeOptResponse res = MBeanService.getInstance().invokeOp(form);

		return res;
	}

	@Override
	public String getRequestUrl() {
		return "/invokeOpt";
	}

}
