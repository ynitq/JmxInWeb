package com.ynitq.utils.jmxInWeb.mvc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.ynitq.utils.jmxInWeb.exception.BaseLogicException;
import com.ynitq.utils.jmxInWeb.http.MyHttpRequest;

/**
 * <pre>
 * 所有action的基类，根据这个基类会派生出处理页面和ajax的两个子类
 * </pre>
 * 
 * @see BasePageAction 处理页面的子类，内置了freemarker模板引擎的调用
 * @see BaseAjaxAction 处理ajax的子类，内置了将BaseJsonResponse变成json格式后再输出的方法
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a>
 * 2015年9月11日
 */
public abstract class BaseAction {

	public abstract String getRequestUrl();

	/**
	 * 用于创建Model，我们可以在这个方法中加入所有action都需要的数据
	 * @return
	 */
	protected Map<String, Object> newModel() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}

	public abstract void process(MyHttpRequest request) throws IOException, BaseLogicException;

}
