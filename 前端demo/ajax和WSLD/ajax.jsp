
	<script type="text/javascript">
	function showDetail(cid) {
		var but = document.getElementById("but"+cid);
		var tab = document.getElementById("tab"+cid);
		if(but.value == "订单详情"){
			// 1.创建异步对象:
			var xhr = createXMLHttpRequest();
			// 2.设置监听:
			xhr.onreadystatechange = function(){
				if(xhr.readyState == 4){
					if(xhr.status == 200){
						// alert(xhr.responseText);
						var data = xhr.responseText;
						// eval函数
						var json = eval("("+data+")");
						for(var i = 0 ;i<json.length;i++){
							tab.innerHTML += "<tr><td>"+json[i].oid+"</td><td>"+json[i].addr+"</td></tr>";
						}
					}
				}
			}
			// 3.打开连接:
			xhr.open("GET","${pageContext.request.contextPath}/order_findByCid.action?"+new Date().getTime()+"&cid="+cid,true);
			// 4.发送:
			xhr.send(null);
			but.value = "关闭";
		} else {
			but.value = "订单详情";
			tab.innerHTML="";
		}
	}

	function createXMLHttpRequest() {
		var xmlHttp;
		try { // Firefox, Opera 8.0+, Safari
			xmlHttp = new XMLHttpRequest();	// 针对于现在的浏览器包括IE7以上版本
		} catch (e) {
			try {// Internet Explorer
				xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");	 
			} catch (e) {
				try {
						// 针对于IE5,IE6版本
					xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (e) {
				}
			}
		}

		return xmlHttp;
	}
</script>