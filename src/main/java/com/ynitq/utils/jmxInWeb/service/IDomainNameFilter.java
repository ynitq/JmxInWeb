package com.ynitq.utils.jmxInWeb.service;

/**
 * <pre>
 * MBean Domain的过滤器，有时我们并不希望所有的Domain都显示出来
 * </pre>
 * 
 * @author 梁韦江 2016年6月7日
 */
public interface IDomainNameFilter {

	boolean show(String domainName);

}
