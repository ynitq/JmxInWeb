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

	private static interface IValue<T> {
		T toValue(String text);
	}

	private class ToBigDecimal implements IValue<BigDecimal> {

		@Override
		public BigDecimal toValue(String text) {
			if (StringUtils.isNotBlank(text)) {
				return new BigDecimal(text);
			} else {
				return null;
			}
		}
	}

	private class ToBigInteger implements IValue<BigInteger> {

		@Override
		public BigInteger toValue(String text) {
			if (StringUtils.isNotBlank(text)) {
				return new BigInteger(text);
			} else {
				return null;
			}
		}
	}

	private class ToBoolean implements IValue<Boolean> {

		@Override
		public Boolean toValue(String text) {
			return true;
		}
	}

	private class ToByte implements IValue<Byte> {

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
	}

	private class ToChar implements IValue<Character> {

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

	private class ToDate implements IValue<Date> {

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
	}

	private class ToDouble implements IValue<Double> {

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
	}

	private class ToFloat implements IValue<Float> {

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
	}

	private class ToInt implements IValue<Integer> {

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
	}

	private class ToLong implements IValue<Long> {

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
	}

	private class ToString implements IValue<String> {

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

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private final Map<String, IValue<?>> converterMap = new HashMap<String, IValue<?>>();

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

	private void addConverter(Class<?> clazz, IValue<?> converter) {
		this.converterMap.put(clazz.getName(), converter);
	}

	@SuppressWarnings("unchecked")
	private <T> T getValueFormString(String text, Class<T> paramClass) {
		IValue<?> toValue = this.converterMap.get(paramClass.getName());
		if (toValue != null) {
			return (T) toValue.toValue(text);
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
