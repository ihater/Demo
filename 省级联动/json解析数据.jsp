<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>ajax开发---json返回</title>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/my.js"></script>

<script type="text/javascript">
	var jsonObj; //声明全局，因为要在多个函数中使用.
	window.onload = function() {

		var province = document.getElementById("province");//省份下拉框
		//第一步:得到XMLHttpRequest对象.
		var xmlhttp = getXmlHttpRequest();
		//2.设置回调函数
		xmlhttp.onreadystatechange = function() {

			//5.处理响应数据  当信息全部返回，并且是成功
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				jsonObj = eval("(" + xmlhttp.responseText + ")");

				//得到省份名称

				for ( var i = 0; i < jsonObj.length; i++) {
					var pname = jsonObj[i].name;

					var option = document.createElement("option");
					option.text = pname;

					province.add(option);
				}

			}
		};

		//post请求方式，参数设置
		xmlhttp.open("GET", "${pageContext.request.contextPath}/ajax2");

		xmlhttp.send(null);

	};

	//创建一个函数，用于向城市下拉框中添加值.
	function fillCity() {

		var province = document.getElementById("province");//省份下拉框
		var city = document.getElementById("city");//城市下拉框.

		//每一次向城市中添加信息时，将信息重置。
		city.innerHTML = "<option>--请选择城市--</option>";

		var pname = province.options[province.selectedIndex].text;

		for ( var i = 0; i < jsonObj.length; i++) {
			var pElementName = jsonObj[i].name;

			if (pname == pElementName) {
				var citys = jsonObj[i].citys;

				for ( var j = 0; j < citys.length; j++) {

					var cname = citys[j].name;

					var option = document.createElement("option");
					option.text = cname;

					city.add(option);

				}
			}
		}

	}
</script>

</head>

<body>
	<select id="province" onchange="fillCity()">
		<option>--请选择省份--</option>
	</select>
	<select id="city">
		<option>--请选择城市--</option>
	</select>
</body>
</html>
