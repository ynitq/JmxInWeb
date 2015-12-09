package com.ynitq.utils.jmxInWeb.http;

import java.util.List;

import com.ynitq.utils.jmxInWeb.mvc.BaseAction;

/**
 * <pre>
 * 获得所有Action的接口
 * </pre>
 * 
 * @author <a href="https://github.com/liangwj72">Alex (梁韦江)</a>
 * 2015年12月9日
 */
public interface IActionContainer {

	List<BaseAction> getAllActions();
	
}
