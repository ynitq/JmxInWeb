package com.ynitq.utils.jmxInWeb.service;

import java.util.List;

import javax.management.JMException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ynitq.utils.jmxInWeb.SimpleSpingSupport;
import com.ynitq.utils.jmxInWeb.models.DomainVo;
import com.ynitq.utils.jmxInWeb.models.MBeanAttrVo;
import com.ynitq.utils.jmxInWeb.models.MBeanOpVo;
import com.ynitq.utils.jmxInWeb.models.MBeanVo;
import com.ynitq.utils.jmxInWeb.service.MBeanService;
import com.ynitq.utils.jmxInWeb.service.testMBeans.MBean1;

/**
 * <pre>
 * MBeanService 测试类
 * </pre>
 * 
 * @author<a href="https://github.com/liangwj72">Alex (梁韦江)</a> 2015年10月15日
 */
public class MBeanServiceTest {

	private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(MBeanServiceTest.class);

	private static final SimpleSpingSupport helper = new SimpleSpingSupport();
	private static final MBeanService service = MBeanService.getInstance();

	@BeforeClass
	public static void beforeClass() {
		log.info("BeforeClass init MBeanService");
		service.setServer(helper.getMBeanServer());

		// 注册一个测试的mbean
		helper.register(new MBean1());

	}

	/**
	 * 测试能获得的所有domain,至少有一个JMImplementation:type=MBeanServerDelegate
	 */
	@Test
	public void testAll() {
		log.debug("Test getAllMBaen()");

		List<DomainVo> list = service.getAllMBaen();
		Assert.assertTrue("at least 1 domain", !list.isEmpty());

		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%d domain found\n", list.size()));
		for (DomainVo domainVo : list) {
			sb.append("\t");
			sb.append(String.format("domain:%s -- (%d MBean)\n", domainVo.getName(), domainVo.getBeans().size()));

			for (MBeanVo vo : domainVo.getBeans()) {
				sb.append("\t\t");
				sb.append(String.format("%s\n", vo.getSimpleName()));
			}
		}
		log.debug(sb.toString());
	}

	@Test
	public void getMBeanInfo() throws JMException {

		log.debug("Test getMBeanByName()");

		// register a mbean
		Object mbean = new MBean1();
		helper.register(mbean);

		// create ObjectName
		Class<?> clazz = mbean.getClass();
		String objectName = String.format("%s:name=%s", clazz.getPackage().getName(), clazz.getSimpleName());

		// test find this mbean
		MBeanVo mbeanVo = service.getMBeanByName(objectName);
		Assert.assertNotNull(mbeanVo);// not null

		StringBuffer sb = new StringBuffer();
		sb.append(String.format("MBean:%s\n", mbeanVo.getSimpleName()));

		sb.append("\t --------- Attributes ------\n");
		mbeanVo.getAttrs();
		for (MBeanAttrVo attr : mbeanVo.getAttrs()) {
			sb.append(String.format("\t%s:%s\n", attr.getInfo().getName(), attr.toDebugString()));
		}

		sb.append("\t --------- Operations ------\n");
		mbeanVo.getAttrs();
		for (MBeanOpVo opt : mbeanVo.getOpts()) {
			sb.append(String.format("\t%s\n", opt.toDebugString()));
		}

		log.debug(sb.toString());

	}

}
