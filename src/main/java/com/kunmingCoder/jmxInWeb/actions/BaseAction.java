package com.kunmingCoder.jmxInWeb.actions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import com.kunmingCoder.jmxInWeb.service.TemplateService;
import com.sun.net.httpserver.HttpExchange;

import freemarker.template.TemplateException;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 梁韦江
 * 2015年9月11日
 */
public abstract class BaseAction implements IRequestHandler {

	public abstract String getRequestUrl();

	protected Map<String, Object> newModel() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}

	protected String genViewPage(String viewName, Map<String, Object> dataModel) throws TemplateException, IOException {
		return TemplateService.getInstance().process(viewName, dataModel);
	}

	// protected void test() {
	// HttpStatus statusCode = getHttp11StatusCode(request, response,
	// targetUrl); 302
	// response.setStatus( statusCode.value());
	// response.setHeader("Location", encodedRedirectURL);
	// }

	/**
	 * 响应的是字节流
	 * 
	 * @param httpExchange
	 * @param sendBytes
	 * @throws IOException
	 */
	protected void sendResponse(HttpExchange httpExchange, byte[] sendBytes) throws IOException {

		httpExchange.getResponseHeaders().set("Content-Encoding", "gzip");
		httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		final GZIPOutputStream os = new GZIPOutputStream(httpExchange.getResponseBody());
		os.write(sendBytes);
		os.finish();
		os.close();
	}

	/**
	 * 响应的是字符
	 * 
	 * @param httpExchange
	 * @param body
	 * @throws IOException
	 */
	protected void sendResponse(HttpExchange httpExchange, String body) throws IOException {
		this.sendResponse(httpExchange, body.getBytes());
	}
}
