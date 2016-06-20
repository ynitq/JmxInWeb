package com.ynitq.utils.jmxInWeb.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <pre>
 * 为了减少依赖包，从各个地方拷贝过来的字符串处理代码，
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年9月13日
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
		// InputStream is = ClassLoader.getSystemResourceAsStream(fileName);
		InputStream is = StringUtils.class.getClassLoader().getResourceAsStream(fileName);
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

	/**
	 * 是否null或者只有空字符
	 * 
	 * @param str
	 * @return
	 */
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

	/**
	 * 是否非空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return (!(isBlank(str)));
	}

	/**
	 * 将字符串的第一个字符专程为大写
	 * 
	 * @param str
	 * @return
	 */
	public static String upperCaseFirstChar(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char[] ca = str.toCharArray();
		ca[0] = Character.toUpperCase(ca[0]);
		return new String(ca);
	}

	/**
	 * 将字符串的第一个字符小写
	 * 
	 * @param str
	 * @return
	 */
	public static String lowerCaseFirstChar(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char[] ca = str.toCharArray();
		ca[0] = Character.toLowerCase(ca[0]);
		return new String(ca);
	}

	/**
	 * 获得字符串的简述
	 * 
	 * @param str
	 * @param lengthLimit
	 *            长度限制
	 * @return
	 */
	public static String getStrSummary(String str, int lengthLimit) {
		if (str == null) {
			return "NULL";
		} else {
			if (str.length() < lengthLimit) {
				return str;
			} else {
				return String.format("%s...(%d)", str.substring(0, lengthLimit), str.length());
			}
		}
	}

	public static String html(String in, boolean space) {
		if (in == null || in.length() == 0) {
			return "";
		}
		char[] charArray = in.toCharArray();
		StringBuffer strBuff = new StringBuffer();

		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			switch (c) {
			case '\r':
				break;
			case '&':
				strBuff.append("&amp;");
				break;
			case '<':
				strBuff.append("&lt;");
				break;
			case '>':
				strBuff.append("&gt;");
				break;
			case '\"':
				strBuff.append("&quot;");
				break;
			case '\n':
				strBuff.append("<br/>");
				break;
			case ' ':
			case '\t':
				if (space) {
					strBuff.append(" &nbsp;");
				} else {
					strBuff.append(c);
				}
				break;
			default:
				strBuff.append(c);
			}

		}
		return strBuff.toString();
	}

}
