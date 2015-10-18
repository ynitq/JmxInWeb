package com.senatry.jmxInWeb.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.Attribute;
import javax.management.JMException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import com.senatry.jmxInWeb.actions.mbean.AjaxChangeAttrForm;
import com.senatry.jmxInWeb.actions.mbean.AjaxInvokeOpForm;
import com.senatry.jmxInWeb.exception.BaseLogicException;
import com.senatry.jmxInWeb.exception.MyAttrNotFoundException;
import com.senatry.jmxInWeb.exception.MyInvalidParamTypeException;
import com.senatry.jmxInWeb.exception.MyMBeanNotFoundException;
import com.senatry.jmxInWeb.exception.MyMalformedObjectNameException;
import com.senatry.jmxInWeb.exception.MyOperationNotFoundException;
import com.senatry.jmxInWeb.json.JsonInvokeOptResponse;
import com.senatry.jmxInWeb.models.DomainVo;
import com.senatry.jmxInWeb.models.MBeanVo;
import com.senatry.jmxInWeb.utils.LogUtil;
import com.senatry.jmxInWeb.utils.OpenTypeUtil;
import com.senatry.jmxInWeb.utils.StringUtils;

/**
 * <pre>
 * 同MBeanServer 获取MBean的各类信息，以及相关操作
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
			MBeanVo vo = new MBeanVo(objectName, info);
			vo.setValueFromMBeanServer(server);
			return vo;
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

	/**
	 * 根据输入的字符串查找mbean
	 * 
	 * @param name
	 * @return
	 * @throws JMException
	 * @throws MyMBeanNotFoundException
	 */
	private MBeanInfo getMBeanInfoByName(ObjectName objectName) throws JMException, MyMBeanNotFoundException {
		if (server.isRegistered(objectName)) {
			return server.getMBeanInfo(objectName);
		} else {
			throw new MyMBeanNotFoundException(objectName.getCanonicalName());
		}
	}

	private ObjectName getObjectName(String nameStr) throws MyMalformedObjectNameException {
		try {
			ObjectName objectName = new ObjectName(nameStr);
			return objectName;
		} catch (MalformedObjectNameException e) {
			throw new MyMalformedObjectNameException(nameStr);
		}
	}

	/**
	 * 修改属性值
	 * 
	 * @param form
	 * @return
	 * @throws BaseLogicException
	 * @throws JMException
	 */
	public String changeAttrValue(AjaxChangeAttrForm form) throws BaseLogicException, JMException {

		MBeanAttributeInfo targetAttribute = null;

		ObjectName name = this.getObjectName(form.getObjectName());

		// Find target attribute
		MBeanInfo info = this.getMBeanInfoByName(name);
		MBeanAttributeInfo[] attributes = info.getAttributes();
		if (attributes != null) {
			for (int i = 0; i < attributes.length; i++) {
				if (attributes[i].getName().equals(form.getName())) {
					targetAttribute = attributes[i];
					break;
				}
			}
		}
		if (targetAttribute == null) {
			throw new MyAttrNotFoundException(form.getName());
		}

		String type = targetAttribute.getType();
		Object value = OpenTypeUtil.parserFromString(form.getValue(), type);
		server.setAttribute(name, new Attribute(form.getName(), value));

		String valueStr = OpenTypeUtil.toString(value, type);
		if (log.isDebugEnabled()) {
			log.debug(LogUtil.format("Change Attr Value:ObjectName=%s AttrName=%s inputValue=%s value=%s", form.getObjectName(),
					form.getName(),
					form.getValue(), valueStr));
		}
		return valueStr;

	}

	/**
	 * invoke op
	 * 
	 * @param form
	 * @return
	 * @throws JMException
	 * @throws MyMBeanNotFoundException
	 * @throws MyMalformedObjectNameException
	 * @throws MyOperationNotFoundException
	 * @throws MyInvalidParamTypeException
	 */
	public JsonInvokeOptResponse invokeOp(AjaxInvokeOpForm form)
			throws MyMBeanNotFoundException, JMException, MyMalformedObjectNameException, MyOperationNotFoundException,
			MyInvalidParamTypeException {
		ObjectName name = this.getObjectName(form.getObjectName());

		// Find target attribute
		MBeanInfo info = this.getMBeanInfoByName(name);

		// get param info
		AjaxInvokeOpForm.ParamInfo paramInfo = form.getParamInfo();

		// check param match
		MBeanOperationInfo[] operations = info.getOperations();
		MBeanOperationInfo targetOperation = null;
		if (operations != null) {
			for (int j = 0; j < operations.length; j++) {
				if (operations[j].getName().equals(form.getOptName())) {
					if (paramInfo.isMath(operations[j].getSignature())) {
						targetOperation = operations[j];
						break;
					}
				}
			}
		}

		if (targetOperation == null) {
			throw new MyOperationNotFoundException(paramInfo.getOperationsInfo());
		}

		Object returnValue = server.invoke(name, form.getOptName(), paramInfo.getValues(), paramInfo.getTypes());

		if (log.isDebugEnabled()) {
			log.debug(LogUtil.format("invode %s %s value=%s", targetOperation.getReturnType(), paramInfo.getOperationsInfo(),
					returnValue));
		}

		JsonInvokeOptResponse res = new JsonInvokeOptResponse();
		res.setReturnData(returnValue);
		res.setHasReturn(!targetOperation.getReturnType().equals("void"));
		res.setOpName(form.getOptName());

		return res;
	}

}
