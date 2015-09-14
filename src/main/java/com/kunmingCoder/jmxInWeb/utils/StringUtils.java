package com.kunmingCoder.jmxInWeb.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 梁韦江 2015年9月13日
 */
public class StringUtils {

	/**
	 * 从classpath中，将文件读入到byte[]
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static byte[] loadFileFromClassPath(String fileName) throws IOException {
		InputStream is = ClassLoader.getSystemResourceAsStream(fileName);
		if (is != null) {
			ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
			byte[] buf = new byte[4096];
			int len;
			while ((len = is.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			byte[] bytes = out.toByteArray();
			return bytes;
		} else {
			return null;
		}
	}

	public static boolean isBlank(String str) {
		int strLen;
		if ((str == null) || ((strLen = str.length()) == 0)) {
			return true;
		}
		for (int i = 0; i < strLen; ++i) {
			if (!(Character.isWhitespace(str.charAt(i)))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(String str) {
		return (!(isBlank(str)));
	}

	public static String upperCaseFirstChar(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char[] ca = str.toCharArray();
		ca[0] = Character.toUpperCase(ca[0]);
		return new String(ca);
	}

	public static String lowerCaseFirstChar(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char[] ca = str.toCharArray();
		ca[0] = Character.toLowerCase(ca[0]);
		return new String(ca);
	}

	public static String getStrSummary(String str, int len) {
		if (str == null) {
			return "NULL";
		} else {
			if (str.length() < len) {
				return str;
			} else {
				return String.format("%s...(%d)", str.substring(0, len), str.length());
			}
		}
	}

}
