package com.senatry.jmxInWeb.exception;

import java.util.LinkedList;
import java.util.List;

import com.senatry.jmxInWeb.json.JsonFieldErrorBean;

/**
 * <pre>
 * 缺少参数
 * </pre>
 * 
 * @author 梁韦江 2015年10月15日
 */
public class MissingParamException extends BaseLogicException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final List<JsonFieldErrorBean> errorList = new LinkedList<JsonFieldErrorBean>();

	public MissingParamException() {
		super();
	}

	public void addMissingParam(String fieldName, String errorMsg) {
		this.errorList.add(new JsonFieldErrorBean(fieldName, errorMsg));
	}

	public List<JsonFieldErrorBean> getErrorList() {
		return errorList;
	}

}
