package com.senatry.jmxInWeb.http;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import com.senatry.jmxInWeb.config.AppConstants;
import com.senatry.jmxInWeb.utils.BinderUtil;
import com.sun.net.httpserver.HttpExchange;

/**
 * <pre>
 * 对HttpExchange的封装
 * </pre>
 * 
 * @see HttpExchange
 * @author 梁韦江 2015年10月14日
 */
public class MyHttpRequest implements Closeable {

	private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(MyHttpRequest.class);

	private final HttpExchange httpExchange;

	private final String path;

	public MyHttpRequest(HttpExchange httpExchange) {
		super();
		this.httpExchange = httpExchange;
		this.path = httpExchange.getRequestURI().getPath();

		this.debugHttpRequest();
	}

	/**
	 * 响应的是字节流
	 * 
	 * @param httpExchange
	 * @param sendBytes
	 * @throws IOException
	 */
	public void sendResponse(byte[] sendBytes) throws IOException {

		this.httpExchange.getResponseHeaders().set("Content-Encoding", "gzip");
		this.httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
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
	public void sendResponse(String body) throws IOException {
		this.sendResponse(body.getBytes());
	}

	/**
	 * redirect
	 * 
	 * @param url
	 * @throws IOException
	 */
	public void redirect(String url) throws IOException {

		try {
			String encodedRedirectURL = URLEncoder.encode(url, "utf-8");
			this.httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_MOVED_TEMP, 0);// 302
			this.httpExchange.getRequestHeaders().set("Location", encodedRedirectURL);

		} catch (UnsupportedEncodingException ex) {

		}
	}

	public void error404() throws IOException {
		String body = "404";
		this.httpExchange.getResponseHeaders().set("Content-Encoding", "gzip");
		this.httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
		final GZIPOutputStream os = new GZIPOutputStream(httpExchange.getResponseBody());
		os.write(body.getBytes());
		os.finish();
		os.close();
	}

	private void debugHttpRequest() {
		if (log.isDebugEnabled()) {
			StringBuffer buff = new StringBuffer(200);
			buff.append("path=").append(httpExchange.getRequestURI().getPath());

			List<String> list = ParameterFilter.getParameterNames(httpExchange);
			for (String name : list) {
				String value = ParameterFilter.getParameter(httpExchange, name);
				buff.append("\n\t");
				buff.append(name);
				buff.append("=");
				buff.append(value);
			}

			log.debug(buff.toString());
		}

	}

	/**
	 * 是否是对静态文件的请求
	 * 
	 * @return
	 */
	public boolean isStaticFileRequest() {
		return this.path.startsWith(AppConstants.STATICS_PREFIX);
	}

	public String getPath() {
		return path;
	}

	@Override
	public void close() throws IOException {
		this.httpExchange.close();
	}

	/**
	 * 将httpRequest中的参数注入到form中
	 * 
	 * @param formClass
	 * @return
	 */
	public <T> T bindForm(Class<T> formClass) {
		return BinderUtil.bindForm(httpExchange, formClass);
	}

}
