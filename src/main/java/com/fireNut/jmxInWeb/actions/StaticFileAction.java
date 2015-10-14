package com.fireNut.jmxInWeb.actions;

import java.io.IOException;

import com.fireNut.jmxInWeb.AppConstants;
import com.fireNut.jmxInWeb.http.BaseAction;
import com.fireNut.jmxInWeb.http.MyHttpRequest;
import com.fireNut.jmxInWeb.utils.StringUtils;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 梁韦江
 * 2015年10月13日
 */
public class StaticFileAction extends BaseAction {

	@Override
	public void process(MyHttpRequest request) throws IOException {
		String fileName = request.getPath().substring(AppConstants.STATICS_PREFIX.length());
		byte[] sendBytes = StringUtils.loadFileFromClassPath(AppConstants.STATIC_RESOURCE_PREFIX + fileName);
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
