package com.kunmingCoder.jmxInWeb.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import com.kunmingCoder.jmxInWeb.utils.ResourceTemplateLoader;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 梁韦江 2015年9月13日
 */
public class TemplateService {

	private final Configuration freemarkerCfg;

	private final static TemplateService instance = new TemplateService();

	public static TemplateService getInstance() {
		return instance;
	}

	private TemplateService() {
		freemarkerCfg = new Configuration(Configuration.getVersion());
		freemarkerCfg.setTemplateLoader(new ResourceTemplateLoader());
	}

	/**
	 * 根据模板和数据生成页面
	 * 
	 * @param templateName
	 *            模板文件名
	 * @param dataModel
	 *            数据
	 * @return
	 * @throws TemplateException
	 * @throws IOException
	 */
	public String process(String templateName, Object dataModel) throws TemplateException, IOException {

		Template template = this.freemarkerCfg.getTemplate(templateName);
		StringWriter w = new StringWriter();
		PrintWriter out = new PrintWriter(w);
		template.process(dataModel, out);

		return w.toString();
	}
}
