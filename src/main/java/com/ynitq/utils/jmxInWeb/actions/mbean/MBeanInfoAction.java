package com.ynitq.utils.jmxInWeb.actions.mbean;

import java.io.IOException;
import java.util.Map;

import javax.management.JMException;

import com.ynitq.utils.jmxInWeb.exception.MyMissingParamException;
import com.ynitq.utils.jmxInWeb.http.MyHttpRequest;
import com.ynitq.utils.jmxInWeb.models.MBeanVo;
import com.ynitq.utils.jmxInWeb.mvc.BasePageAction;
import com.ynitq.utils.jmxInWeb.service.MBeanService;

/**
 * <pre>
 * 获得mbean的信息
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年10月15日
 */
public class MBeanInfoAction extends BasePageAction {

	@Override
	public String getRequestUrl() {
		return "/mbeanInfo";
	}

	@Override
	protected String getModelAndView(MyHttpRequest request, Map<String, Object> dataModel)
			throws IOException, JMException, MyMissingParamException {

		ObjectNameForm form = request.bindForm(ObjectNameForm.class);

		form.verifyObjectName();

		MBeanVo mbean = MBeanService.getInstance().getMBeanByName(form.getObjectName());
		if (mbean != null) {
			dataModel.put("mbean", mbean);
			return "mbeanInfo.html";
		} else {
			// 如果找不到mbean的就返回首页
			request.redirect("/");
			return null;
		}

	}

}
