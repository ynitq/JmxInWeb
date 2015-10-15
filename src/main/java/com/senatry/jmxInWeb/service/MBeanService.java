package com.senatry.jmxInWeb.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.JMException;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import com.senatry.jmxInWeb.models.DomainVo;
import com.senatry.jmxInWeb.models.MBeanVo;
import com.senatry.jmxInWeb.utils.LogUtil;
import com.senatry.jmxInWeb.utils.StringUtils;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author 梁韦江 2015年9月11日
 */
public class MBeanService {

	private static final org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(MBeanService.class);

	/**
	 * Target server
	 */
	private MBeanServer server;

	private static final MBeanService instance = new MBeanService();

	public static MBeanService getInstance() {
		return instance;
	}

	public MBeanService() {
	}

	public MBeanServer getServer() {
		return server;
	}

	public void setServer(MBeanServer server) {
		this.server = server;
	}

	/**
	 * 根据全名，获得mbean
	 * 
	 * @param name
	 * @return
	 * @throws JMException
	 */
	public MBeanVo getMBeanByName(String name) throws JMException {
		if (StringUtils.isBlank(name)) {
			return null;
		}

		ObjectName objectName = new ObjectName(name);
		if (!objectName.isPattern() && server.isRegistered(objectName)) {
			MBeanInfo info = server.getMBeanInfo(objectName);
			return new MBeanVo(objectName, info);
		}

		return null;
	}

	public List<DomainVo> getAllMBaen() {

		List<DomainVo> domainList = new LinkedList<DomainVo>();

		Set<ObjectInstance> mbeans = server.queryMBeans(null, null);

		Map<String, DomainVo> domainMap = new HashMap<String, DomainVo>();
		for (ObjectInstance instance : mbeans) {

			ObjectName name = instance.getObjectName();

			String domainName = name.getDomain();
			DomainVo domainVo = domainMap.get(domainName);
			if (domainVo == null) {
				domainVo = new DomainVo(domainName);
				domainMap.put(domainName, domainVo);
			}

			try {
				MBeanInfo info = server.getMBeanInfo(name);
				domainVo.addMBean(name, info);
			} catch (Exception e) {
				LogUtil.traceError(log, e);
			}
		}
		domainList.addAll(domainMap.values());
		Collections.sort(domainList);
		return domainList;
	}

}