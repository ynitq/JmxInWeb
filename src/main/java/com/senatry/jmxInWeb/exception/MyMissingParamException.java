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
public class MyMissingParamException extends BaseLogicException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final List<JsonFieldErrorBean> errorList = new LinkedList<JsonFieldErrorBean>();

	public MyMissingParamException() {
		super("");
	}

	public void addMissingParam(String fieldName, String errorMsg) {
		this.errorList.add(new JsonFieldErrorBean(fieldName, errorMsg));
	}

	public List<JsonFieldErrorBean> getErrorList() {
		return errorList;
	}

	@Override
	public String getMessage() {
		StringBuffer sb = new StringBuffer();
		boolean first = true;
		for (JsonFieldErrorBean jsonFieldErrorBean : errorList) {
			if (first) {
				first = false;
			} else {
				sb.append(",");
			}

			sb.append(jsonFieldErrorBean.getFieldName());
		}
		return String.format("缺少参数: %s", sb.toString());
	}

}
