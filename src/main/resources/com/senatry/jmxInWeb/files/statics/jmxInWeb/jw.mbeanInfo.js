// JavaScript Document

$(document).ready(function() {

	/**
	 * 执行opt
	 */
	$('.js_invoke_opt').click(function(e) {
		var formName = "#"+$(this).attr('data-form-name');
		console.log("Click on Invoke Btn, form=" + formName);

		var form=$(formName);
		var param = form.serialize();
		$.post("/invokeOpt", param, function(res) {
			console.log(res);
			if (res.success) {
				toastr["success"](res.opName,"Invode Success");
				if (res.hasReturn) {
					$("#invodeResult_body").text(res.returnData);
					$("#invokeResult_modal").modal();
				}
			} else {
			}
		}, "json");

	});

	/**
	 * Modify mbean attribute value. 修改MBean属性的链接 We use Bootstrap Editable
	 * 
	 * @see http://vitalets.github.io/x-editable/docs.html
	 */
	$('.js_edit_attr').each(function(index, element) {

		// option
		var option = {
			url : '/ajaxChangeAttr',
			ajaxOptions : {
				type : 'post',
				dataType : 'json'
			},
			type  : 'textarea',
			mode  :'inline',
			value : '', // 初始值为空
			params : function(params) {
				params.objectName = $(this).attr("data-param-objectName");
				return params;
			},
			pk : 1,
			display : false,// 默认修改完成后不修改界面上的值
			success : function(response, newValue) {
				if (!response.success) {
					// @see Java JsonErrorResponse
					return response.errorMsg;
				} else {
					return response;
				}
			},
		}
		
		var valueType = $(this).attr('data-value-type');
		if (valueType == 'boolean' || valueType == 'java.lang.Boolean') {
			option.type = 'select';
            option.source = [{
	                value: 'false',
	                text: 'false'
	            }, {
	                value: 'true',
	                text: 'true'
	            }
	        ];
            option.showbuttons = false;
		}
		
		if ($(this).attr("data-readable") == "true") {
			// 如果该属性可读
			option.value = $(this).text();
			option.display = null;
		}

		$(this).editable(option);
	});
});
