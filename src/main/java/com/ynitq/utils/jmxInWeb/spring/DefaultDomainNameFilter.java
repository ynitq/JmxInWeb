package com.ynitq.utils.jmxInWeb.spring;

import java.util.HashSet;
import java.util.Set;

import com.ynitq.utils.jmxInWeb.service.IDomainNameFilter;

/**
 * <pre>
 * 默认的domain过滤器，主要是过滤spring boot 中的过多的mbean
 * </pre>
 * 
 * @author 梁韦江 2016年6月7日
 */
public class DefaultDomainNameFilter implements IDomainNameFilter {

	private Set<String> ignoreSet = new HashSet<>();

	public DefaultDomainNameFilter() {
		this.addIgnoreDomain("Tomcat");
		this.addIgnoreDomain("Tomcat-1");
		this.addIgnoreDomain("com.sun.management");
		this.addIgnoreDomain("java.lang");
		this.addIgnoreDomain("java.nio");
		this.addIgnoreDomain("java.util.logging");
		this.addIgnoreDomain("JMImplementation");
	}

	@Override
	public boolean show(String domainName) {
		if (domainName == null) {
			return false;
		} else {
			return !this.ignoreSet.contains(domainName.toLowerCase());
		}
	}

	public void addIgnoreDomain(String domainName) {
		if (domainName != null) {
			this.ignoreSet.add(domainName.toLowerCase());
		}
	}

	public void removeIgnoreDomain(String domainName) {
		if (domainName != null) {
			this.ignoreSet.remove(domainName.toLowerCase());
		}
	}
}
