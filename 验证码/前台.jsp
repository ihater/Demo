	function change(){
		var img = document.getElementById("checkImg");
		img.src="${pageContext.request.contextPath}/checkImg.action?"+new Date().getTime();
	}					// ���л��棬���ԼӸ�����
	

<action name="checkImg" class="user.checkImg"></action>

<img   id="checkImg" class="captchaImage"
	src="${ pageContext.request.contextPath }/checkImg.action"
	title="���������֤��" onclick="change()"/>