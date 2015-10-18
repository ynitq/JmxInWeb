package com.senatry.jmxInWeb.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 用于将字符串转换成为对应的类型
 * </pre>
 * 
 * @author 梁韦江 2015年10月15日
 */
public class OpenTypeUtil {

	private abstract class BaseConverter<T> {
		abstract T toValue(String text);

		String getdefaultValue() {
			return "";
		}

		String toStringValue(Object t) {
			return t.toString();
		}
	}

	private class ToBigDecimal extends BaseConverter<BigDecimal> {

		@Override
		public BigDecimal toValue(String text) {
			if (StringUtils.isNotBlank(text)) {
				return new BigDecimal(text);
			} else {
				return null;
			}
		}

		@Override
		String getdefaultValue() {
			return "0";
		}
	}

	private class ToBigInteger extends BaseConverter<BigInteger> {

		@Override
		public BigInteger toValue(String text) {
			if (StringUtils.isNotBlank(text)) {
				return new BigInteger(text);
			} else {
				return null;
			}
		}

		@Override
		String getdefaultValue() {
			return "0";
		}
	}

	private class ToBoolean extends BaseConverter<Boolean> {

		@Override
		public Boolean toValue(String text) {
			if ("false".equalsIgnoreCase(text)) {
				return false;
			}
			return true;
		}

		@Override
		String getdefaultValue() {
			return "false";
		}
	}

	private class ToByte extends BaseConverter<Byte> {

		private final boolean nullable;

		public ToByte(boolean nullable) {
			super();
			this.nullable = nullable;
		}

		@Override
		public Byte toValue(String text) {
			try {
				return Byte.parseByte(text, 16);
			} catch (Exception e) {
				if (this.nullable) {
					return null;
				} else {
					return 0;
				}
			}
		}

		@Override
		String getdefaultValue() {
			return "0";
		}
	}

	private class ToChar extends BaseConverter<Character> {

		private final boolean nullable;

		public ToChar(boolean nullable) {
			super();
			this.nullable = nullable;
		}

		@Override
		public Character toValue(String text) {
			if (StringUtils.isNotBlank(text)) {
				return text.charAt(0);
			} else {
				if (this.nullable) {
					return null;
				} else {
					return '\0';
				}
			}
		}
	}

	private class ToDate extends BaseConverter<Date> {

		@Override
		public Date toValue(String text) {
			// 日期的处理方式
			Date date;
			try {
				date = dateFormat.parse(text);
			} catch (Exception e) {
				date = new Date();
			}
			return date;
		}

		@Override
		String getdefaultValue() {
			return dateFormat.format(new Date());
		}

		@Override
		String toStringValue(Object t) {
			if (t == null) {
				return "";
			} else {
				return dateFormat.format(t);
			}
		}
	}

	private class ToDouble extends BaseConverter<Double> {

		private final boolean nullable;

		public ToDouble(boolean nullable) {
			super();
			this.nullable = nullable;
		}

		@Override
		public Double toValue(String text) {
			try {
				return Double.parseDouble(text);
			} catch (Exception e) {
				if (this.nullable) {
					return null;
				} else {
					return 0.0d;
				}
			}
		}

		@Override
		String getdefaultValue() {
			return "0";
		}
	}

	private class ToFloat extends BaseConverter<Float> {

		private final boolean nullable;

		public ToFloat(boolean nullable) {
			super();
			this.nullable = nullable;
		}

		@Override
		public Float toValue(String text) {
			try {
				return Float.parseFloat(text);
			} catch (Exception e) {
				if (this.nullable) {
					return null;
				} else {
					return 0f;
				}
			}
		}

		@Override
		String getdefaultValue() {
			return "0";
		}
	}

	private class ToInt extends BaseConverter<Integer> {

		private final boolean nullable;

		public ToInt(boolean nullable) {
			super();
			this.nullable = nullable;
		}

		@Override
		public Integer toValue(String text) {
			try {
				return Integer.parseInt(text);
			} catch (Exception e) {
				if (this.nullable) {
					return null;
				} else {
					return 0;
				}
			}
		}

		@Override
		String getdefaultValue() {
			return "0";
		}
	}

	private class ToLong extends BaseConverter<Long> {

		private final boolean nullable;

		public ToLong(boolean nullable) {
			super();
			this.nullable = nullable;
		}

		@Override
		public Long toValue(String text) {
			try {
				return Long.parseLong(text);
			} catch (Exception e) {
				if (this.nullable) {
					return null;
				} else {
					return 0L;
				}
			}
		}

		@Override
		String getdefaultValue() {
			return "0";
		}
	}

	private class ToString extends BaseConverter<String> {

		@Override
		public String toValue(String text) {
			return text;
		}
	}

	private static OpenTypeUtil instance = new OpenTypeUtil();

	public static boolean isOpenType(Class<?> clazz) {
		return instance.isValidType(clazz);
	}

	public static boolean isOpenType(String className) {
		return instance.converterMap.containsKey(className);
	}

	public static <T> T parserFromString(String text, Class<T> paramClass) {
		return instance.getValueFormString(text, paramClass);
	}

	public static Object parserFromString(String text, String classStr) {
		return instance.getValueFormString(text, classStr);
	}

	public static String toString(Object value, String classStr) {
		BaseConverter<?> toValue = instance.converterMap.get(classStr);
		if (toValue != null) {
			return toValue.toStringValue(value);
		}
		return "";
	}

	public static String getDefaultValue(String classNameStr) {
		BaseConverter<?> toValue = instance.converterMap.get(classNameStr);
		if (toValue != null) {
			return toValue.getdefaultValue();
		}
		return "";
	}

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:ss");

	private final Map<String, BaseConverter<?>> converterMap = new HashMap<String, BaseConverter<?>>();

	private OpenTypeUtil() {
		this.addConverter(Date.class, new ToDate());
		this.addConverter(BigDecimal.class, new ToBigDecimal());
		this.addConverter(BigInteger.class, new ToBigInteger());

		this.addConverter(Byte.class, new ToByte(true));
		this.addConverter(byte.class, new ToByte(false));

		this.addConverter(Integer.class, new ToInt(true));
		this.addConverter(int.class, new ToInt(false));

		this.addConverter(Long.class, new ToLong(true));
		this.addConverter(long.class, new ToLong(false));

		this.addConverter(float.class, new ToFloat(false));
		this.addConverter(Float.class, new ToFloat(true));

		this.addConverter(double.class, new ToDouble(false));
		this.addConverter(Double.class, new ToDouble(true));

		this.addConverter(String.class, new ToString());

		this.addConverter(boolean.class, new ToBoolean());
		this.addConverter(Boolean.class, new ToBoolean());

		this.addConverter(Character.class, new ToChar(true));
		this.addConverter(char.class, new ToChar(false));

	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	private void addConverter(Class<?> clazz, BaseConverter<?> converter) {
		this.converterMap.put(clazz.getName(), converter);
	}

	@SuppressWarnings("unchecked")
	private <T> T getValueFormString(String text, Class<T> paramClass) {
		BaseConverter<?> toValue = this.converterMap.get(paramClass.getName());
		if (toValue != null) {
			return (T) toValue.toValue(text);
		}
		return null;
	}

	private Object getValueFormString(String text, String classNameStr) {
		BaseConverter<?> toValue = this.converterMap.get(classNameStr);
		if (toValue != null) {
			return toValue.toValue(text);
		}
		return null;
	}

	private boolean isValidType(Class<?> clazz) {
		if (clazz.isArray()) {
			return this.converterMap.containsKey(clazz.getComponentType().getName());
		} else {
			return this.converterMap.containsKey(clazz.getName());
		}
	}
}
