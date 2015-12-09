/**
 * <pre>
 * 一个小型mvc实现，使用FreeMarker来生成view
 * 
 * 核心是ActionManager，需要将所有的action都添加到ActionManager中
 * 
 * 我们在ActionManager中手工建立所有的url和处理类的关系，有一点点麻烦。
 * 我们其实可以用类似spring mvc的机制，通过扫描包中的类文件，查询类的注解，
 * 然后建立这个url和类的关系，但这是个小型项目，根本就没有多少东西要处理
 * 我们就不折腾了，手工就手工吧。
 * 
 * </pre>
 * 
 * @see com.ynitq.utils.jmxInWeb.http.ActionDispatcher
 * 
 * @author 
 * 2015年12月1日
 */
package com.ynitq.utils.jmxInWeb.mvc;
