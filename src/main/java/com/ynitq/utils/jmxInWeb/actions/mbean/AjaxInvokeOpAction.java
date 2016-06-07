package com.ynitq.utils.jmxInWeb.actions.mbean;

import java.io.IOException;
import java.util.Map;

import javax.management.JMException;

import com.ynitq.utils.jmxInWeb.exception.BaseLogicException;
import com.ynitq.utils.jmxInWeb.http.MyHttpRequest;
import com.ynitq.utils.jmxInWeb.json.JsonInvokeOptResponse;
import com.ynitq.utils.jmxInWeb.mvc.BaseAjaxAction;
import com.ynitq.utils.jmxInWeb.mvc.BaseJsonResponse;
import com.ynitq.utils.jmxInWeb.service.MBeanUtil;

/**
 * <pre>
 * invoke Op
 * 
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年10月16日
 */
public class AjaxInvokeOpAction extends BaseAjaxAction {

	@Override
	protected BaseJsonResponse getJsonResponse(MyHttpRequest request, Map<String, Object> dataModel)
			throws IOException, BaseLogicException, JMException {

		AjaxInvokeOpForm form = request.bindForm(AjaxInvokeOpForm.class);
		form.verify();

		JsonInvokeOptResponse res = MBeanUtil.getInstance().invokeOp(form);

		return res;
	}

	@Override
	public String getRequestUrl() {
		return "/invokeOpt";
	}

}
