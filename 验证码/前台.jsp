	function change(){
		var img = document.getElementById("checkImg");
		img.src="${pageContext.request.contextPath}/checkImg.action?"+new Date().getTime();
	}					// 怕有缓存，所以加个序列
	

<action name="checkImg" class="user.checkImg"></action>

<img   id="checkImg" class="captchaImage"
	src="${ pageContext.request.contextPath }/checkImg.action"
	title="点击更换验证码" onclick="change()"/>