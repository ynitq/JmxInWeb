// JavaScript Document

$(document).ready(function() {
	
	/** 
	 * Modify mbean attribute value. 修改MBean属性的链接
	 * We use Bootstrap Editable
	 * @see http://vitalets.github.io/x-editable/docs.html
	 */
	$('.js_edit_attr').each(function(index, element) {
		
		//默认值
		var initValue="";//初始值为空
		var displayFun = false; //默认修改完成后不修改界面上的值
		
		if ($(this).attr("data-readable")=="true") {
			//如果该属性可读
			initValue = $(this).text();
			var displayFun = null;
		};
		
		var paramsFun = function(params) {
		    params.objectName = $(this).attr("data-param-objectName");
		    return params;
		};
		
		$(this).editable({
			url: '/ajaxChangeAttr',
			ajaxOptions: {
			    type: 'post',
			    dataType: 'json'
			},
			value:initValue,
			params : paramsFun,
			pk: 1,
			display: displayFun,
			success: function(response, newValue) {
			    if (!response.success) {
			    	//@see Java JsonErrorResponse
			    	return response.errorMsg;
			    } else {
			    	return response;
			    }
			},
		});
    });
});
