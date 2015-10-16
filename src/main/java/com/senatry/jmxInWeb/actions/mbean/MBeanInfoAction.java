package com.senatry.jmxInWeb.actions.mbean;

import java.io.IOException;
import java.util.Map;

import javax.management.JMException;

import com.senatry.jmxInWeb.exception.MyMissingParamException;
import com.senatry.jmxInWeb.http.MyHttpRequest;
import com.senatry.jmxInWeb.models.MBeanVo;
import com.senatry.jmxInWeb.mvc.BasePageAction;
import com.senatry.jmxInWeb.service.MBeanService;

/**
 * <pre>
 * 获得mbean的信息
 * </pre>
 * 
 * @author 梁韦江 2015年10月15日
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
