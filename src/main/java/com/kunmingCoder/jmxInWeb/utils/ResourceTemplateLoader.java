package com.kunmingCoder.jmxInWeb.utils;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import com.kunmingCoder.jmxInWeb.AppConstants;

import freemarker.cache.TemplateLoader;

/**
 * <pre>
 * 自己实现的从resource中读取模板的TemplateLoader
 * </pre>
 * 
 * @author 梁韦江 2015年9月13日
 */
public class ResourceTemplateLoader implements TemplateLoader {

	private final Map<String, TemplateSource> sourceMap = new HashMap<String, ResourceTemplateLoader.TemplateSource>();

	@Override
	public void closeTemplateSource(Object templateSource) throws IOException {
	}

	@Override
	public Object findTemplateSource(String fileName) throws IOException {
		if (fileName != null) {
			TemplateSource source = this.sourceMap.get(fileName);
			if (source == null) {
				byte[] bytes = StringUtils.loadFileFromClassPath(AppConstants.TEMPLATE_PATH + fileName);
				if (bytes != null) {
					source = new TemplateSource(fileName, bytes);
					this.sourceMap.put(fileName, source);
				}
			}
			return source;
		}
		return null;
	}

	@Override
	public long getLastModified(Object templateSource) {
		return ((TemplateSource) templateSource).lastModified;
	}

	@Override
	public Reader getReader(Object templateSource, String encoding) throws IOException {
		return new StringReader(((TemplateSource) templateSource).source);
	}

	private static class TemplateSource {
		private final String name;
		private final String source;
		private final long lastModified;

		TemplateSource(String name, byte[] bytes) {
			if (name == null) {
				throw new IllegalArgumentException("name == null");
			}
			if (bytes == null) {
				throw new IllegalArgumentException("bytes == null");
			}
			this.name = name;
			this.source = new String(bytes);
			this.lastModified = System.currentTimeMillis();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof TemplateSource) {
				return this.name.equals(((TemplateSource) obj).name);
			}
			return false;
		}

		@Override
		public int hashCode() {
			return this.name.hashCode();
		}
	}
}
