package com.ynitq.utils.jmxInWeb.actions;

import java.io.IOException;

import com.ynitq.utils.jmxInWeb.config.AppConstants;
import com.ynitq.utils.jmxInWeb.http.MyHttpRequest;
import com.ynitq.utils.jmxInWeb.mvc.BaseAction;
import com.ynitq.utils.jmxInWeb.utils.StringUtils;

/**
 * <pre>
 * 用于处理今天文件
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年10月13日
 */
public class StaticFileAction extends BaseAction {

	@Override
	public void process(MyHttpRequest request) throws IOException {
		String fileName = request.getPath().substring(AppConstants.STATICS_URL_PREFIX.length());
		byte[] sendBytes = StringUtils.loadFileFromClassPath(AppConstants.RESOURCE_URL_OF_STATIC_RESOURCE + fileName);
		if (sendBytes == null) {
			// 404
			request.error404();
		} else {
			request.sendResponse(sendBytes);
		}
	}

	@Override
	public String getRequestUrl() {
		return null;
	}

}
