package com.senatry.jmxInWeb.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

/**
 * <pre>
 * OpenTypeUtil 测试类
 * </pre>
 * 
 * @author 梁韦江 2015年10月15日
 */
public class OpenTypeUtilTest {

	@Test
	public void testBigDecimal() {
		BigDecimal res = OpenTypeUtil.parserFromString("1.1", BigDecimal.class);
		Assert.assertTrue(res.doubleValue() == 1.1);
	}

	@Test
	public void testBigInteger() {
		BigInteger res = OpenTypeUtil.parserFromString("1", BigInteger.class);
		Assert.assertTrue(res.intValue() == 1);
	}

	@Test
	public void testBoolean() {
		Boolean res = OpenTypeUtil.parserFromString("true", Boolean.class);
		Assert.assertTrue(res);
	}

	@Test
	public void testByte() {
		Byte res = OpenTypeUtil.parserFromString("34", Byte.class);
		Assert.assertTrue(res == 0x34);
	}

	@Test
	public void testChar() {
		Character res = OpenTypeUtil.parserFromString("1", Character.class);
		Assert.assertTrue(res == '1');
	}


}
